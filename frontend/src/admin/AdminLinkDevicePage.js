import React, {Component} from 'react';
import {AdminNavigationMenu} from "./AdminNavigationMenu";
import {ACCOUNT_ENTITY, FindAvailableDevices, LinkDeviceToUser} from "./api/AdminApi";
import {GeneralFilterComponent} from "./components/GeneralFilterComponent";
import {ERROR_TOAST, SUCCESS_TOAST} from "../common/components/ToastNotification";
import {Button, FormLabel} from "react-bootstrap";
import {ModalNotification} from "../common/components/ModalNotification";
import {GeneralListComponent} from "./components/GeneralListComponent";

export class AdminLinkDevicePage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            selectedAccount: null,
            selectedDevice: null,
            devices: [],
            notification: {
                show: false,
                type: ERROR_TOAST,
                message: "",
                fields: []
            }
        }
        this.onSelectAccount = this.onSelectAccount.bind(this);
        this.onSelectDevice = this.onSelectDevice.bind(this);
        this.onSave = this.onSave.bind(this);
        this.hideNotification = this.hideNotification.bind(this);
        this.findDevices = this.findDevices.bind(this);
    }

    componentDidMount() {
        this.findDevices();
    }

    onSelectAccount(accountId) {
        this.setState({
            ...this.state,
            selectedAccount: accountId,
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
            .catch(error => {
                this.setState({
                    ...this.state,
                    notification: {
                        show: true,
                        type: ERROR_TOAST,
                        message: error.message,
                        fields: []
                    }
                })
            });
    }

    onSelectDevice(deviceId) {
        this.setState({
            ...this.state,
            selectedDevice: deviceId
        })
    }

    onSave() {
        LinkDeviceToUser(this.state.selectedDevice.id, this.state.selectedAccount.id)
            .then(() => {
                this.setState({
                    ...this.state,
                    devices:
                        this.state.devices.filter((d) => d.id !== this.state.selectedDevice.id),
                    notification: {
                        show: true,
                        type: SUCCESS_TOAST,
                        message: `Device with id ${this.state.selectedDevice.id} has been successfully added to the user with id ${this.state.selectedAccount.id}`,
                        fields: []
                    }
                })
            })
            .catch(error => {
                console.log(error)
                this.setState({
                    ...this.state,
                    notification: {
                        show: true,
                        type: ERROR_TOAST,
                        message: error.message,
                        fields: []
                    }
                })
            });
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
                    <GeneralFilterComponent type={ACCOUNT_ENTITY} filters={accountFilters} showList
                                            selectedId={this.state.selectedAccount}
                                            onSelectItem={this.onSelectAccount}/>
                    <FormLabel>Select from the available devices:</FormLabel>
                    <GeneralListComponent items={this.state.devices} fields={deviceFilters} showList
                                          selectedId={this.state.selectedDevice}
                                          onSelect={this.onSelectDevice}/>
                    <Button variant="primary" onClick={this.onSave}>Link device to user</Button>
                </div>
            </div>
        );
    }
}
