import React, {Component} from 'react';
import {AdminNavigationMenu} from "./components/AdminNavigationMenu";
import {FindAvailableDevices, LinkDeviceToUser} from "./api/AdminApi";
import {GeneralFilterComponent} from "./components/GeneralFilterComponent";
import {Button, FormLabel} from "react-bootstrap";
import {ERROR, ModalNotification, SUCCESS} from "../common/components/ModalNotification";
import {GeneralListComponent} from "./components/GeneralListComponent";
import {ACCOUNT_ENTITY} from "./components/Constants";
import {CLIENT_ROLE} from "../common/auth/Authentication";

export class AdminLinkDevicePage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            selectedAccount: null,
            selectedDevice: null,
            devices: [],
            notification: {
                show: false,
                type: ERROR,
                message: "",
                fields: []
            }
        }
        this.onSelectAccount = this.onSelectAccount.bind(this);
        this.onSelectDevice = this.onSelectDevice.bind(this);
        this.onSave = this.onSave.bind(this);
        this.hideNotification = this.hideNotification.bind(this);
        this.findDevices = this.findDevices.bind(this);
        this.onError = this.onError.bind(this);
    }

    onError(error) {
        this.setState({
            ...this.state,
            notification: {
                show: true,
                type: ERROR,
                message: error.message,
                fields: error.errors
            }
        });
    }

    componentDidMount() {
        this.findDevices();
    }

    onSelectAccount(account) {
        this.setState({
            ...this.state,
            selectedAccount: account,
        })
        this.findDevices();
    }

    findDevices() {
        FindAvailableDevices()
            .then(data => {
                this.setState({
                    ...this.state,
                    devices: data,
                    selectedDevice: data[0]
                })
            })
            .catch(error => this.onError(error));
    }

    onSelectDevice(device) {
        this.setState({
            ...this.state,
            selectedDevice: device
        })
    }

    onSave() {
        if (!this.state.selectedDevice || !this.state.selectedAccount) {
            this.onError({message: "Cannot link device! No device or user has been selected!"})
        }

        LinkDeviceToUser(this.state.selectedDevice.id, this.state.selectedAccount.id)
            .then(() => {
                this.setState({
                    ...this.state,
                    devices:
                        this.state.devices.filter((d) => d.id !== this.state.selectedDevice.id),
                    notification: {
                        show: true,
                        type: SUCCESS,
                        message: `Device with id ${this.state.selectedDevice.id} has been successfully added to the user with id ${this.state.selectedAccount.id}`,
                        fields: []
                    }
                })
            })
            .catch(error => this.onError(error));
    }

    hideNotification() {
        this.setState({
            ...this.state,
            notification: {
                show: false
            }
        })
    }

    render() {
        const accountFilters = ['id', 'name', 'username', 'role'];
        const deviceFilters = ['id', 'address', 'description', 'accountId'];
        return (
            <div className="page-container">
                <AdminNavigationMenu/>
                <div className="page-content d-flex flex-column gap-4 p-4 w-50 m-auto">
                    {this.state.notification.show ?
                        <ModalNotification notification={this.state.notification} onHide={this.hideNotification}/>
                        :
                        <div/>
                    }
                    <FormLabel>Select user:</FormLabel>
                    <GeneralFilterComponent type={ACCOUNT_ENTITY}
                                            filters={accountFilters}
                                            showList
                                            userRole={CLIENT_ROLE}
                                            selectedItem={this.state.selectedAccount}
                                            onSelectItem={this.onSelectAccount}
                                            errorHandler={this.onError}/>
                    <FormLabel>Select from the available devices:</FormLabel>
                    <GeneralListComponent items={this.state.devices}
                                          fields={deviceFilters}
                                          showList
                                          selectedItem={this.state.selectedDevice}
                                          onSelect={this.onSelectDevice}/>
                    <Button variant="primary" onClick={this.onSave}>Link device to user</Button>
                </div>
            </div>
        );
    }
}
