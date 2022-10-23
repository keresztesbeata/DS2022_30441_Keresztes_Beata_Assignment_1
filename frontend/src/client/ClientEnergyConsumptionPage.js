import React, {Component} from 'react';
import {ERROR, ModalNotification, WARNING} from "../common/components/ModalNotification";
import {ClientNavigationMenu} from "./components/ClientNavigationMenu";
import {Button, FormControl, FormLabel, FormSelect, InputGroup} from "react-bootstrap";
import {GetDevicesOfClient, GetEnergyConsumptionByDay, GetEnergyConsumptionByDayAndDeviceId} from "./api/ClientApi";
import Plot from "react-plotly.js";

export class ClientEnergyConsumptionPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            selectedDate: new Date().toISOString().split('T')[0],
            selectedDeviceId: null,
            energyData: [],
            deviceEnergyData: [],
            devices: [],
            notification: {
                show: false,
                type: ERROR,
                message: "",
                fields: []
            }
        }
        this.onLoad = this.onLoad.bind(this);
        this.hideNotification = this.hideNotification.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.onError = this.onError.bind(this);
        this.generateReport = this.generateReport.bind(this);
        this.generateDeviceReport = this.generateDeviceReport.bind(this);
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

    handleInputChange(event) {
        this.setState({
            ...this.state,
            [event.target.name]: event.target.value
        });
    }

    hideNotification() {
        this.setState({
            ...this.state,
            notification: {
                ...this.state.notification,
                show: false
            }
        });
    }

    componentDidMount() {
        this.onLoad();
    }

    onLoad() {
        GetDevicesOfClient()
            .then(data => {
                this.setState({
                    ...this.state,
                    devices: data,
                    selectedDeviceId: data === null || data.length === 0 ? "" : data[0].id,
                    notification: {
                        show: false
                    }
                });
            })
            .catch(error => this.onError(error));
    }

    generateReport() {
        GetEnergyConsumptionByDay(this.state.selectedDate)
            .then(data => {
                this.setState({
                    ...this.state,
                    energyData: data,
                    notification: {
                        show: false
                    }
                })
            })
            .catch(error => this.onError(error));
    }

    generateDeviceReport() {
        if (this.state.selectedDeviceId === null) {
            this.setState({
                ...this.state,
                deviceEnergyData: [],
                notification: {
                    show: true,
                    type: WARNING,
                    message: "No device selected!",
                    fields: []
                }
            })
        }else {
            GetEnergyConsumptionByDayAndDeviceId(this.state.selectedDate, this.state.selectedDeviceId)
                .then(data => {
                    this.setState({
                        ...this.state,
                        deviceEnergyData: data,
                        notification: {
                            show: false
                        }
                    })
                })
                .catch(error => this.onError(error));
        }
    }

    render() {
        return (
            <div className="page-container">
                <ClientNavigationMenu/>
                {this.state.notification.show ?
                    <ModalNotification notification={this.state.notification} onHide={this.hideNotification}/>
                    :
                    <div/>
                }
                <div className="page-content d-flex flex-column gap-2 p-4 m-auto">
                    <div className="plot-container">
                        <InputGroup className="m-sm-2 gap-lg-3">
                            <FormLabel>Select a day:</FormLabel>
                            <FormControl type="date" name="selectedDate" defaultValue={this.state.selectedDate}
                                         onChange={this.handleInputChange}/>
                            <Button onClick={this.generateReport} variant="success">Generate report</Button>
                        </InputGroup>
                        <Plot data={[{
                            x: this.state.energyData.map(d => d.timestamp),
                            y: this.state.energyData.map(d => d.energy),
                            type: 'bar',
                            marker: {
                                color: "#10555e"
                            }
                        }]} layout={{
                            title: 'Hourly Energy Consumption',
                            autosize: true,
                            xaxis: {
                                title: {
                                    text: 'Hour',
                                },
                            },
                            yaxis: {
                                title: {
                                    text: 'Energy consumption [kWh]',
                                }
                            }
                        }
                        }/>
                    <InputGroup className="m-sm-2 gap-lg-3">
                        <FormLabel>Select a device:</FormLabel>
                        <FormSelect onChange={this.handleInputChange} name="selectedDeviceId">
                            {
                                this.state.devices.map(device =>
                                    <option key={device.id} value={device.id}>
                                        {device.id}
                                    </option>)
                            }
                        </FormSelect>
                        <Button onClick={this.generateDeviceReport} variant="success">Generate report for device</Button>
                    </InputGroup>
                        <Plot data={[{
                            x: this.state.deviceEnergyData.map(d => d.timestamp),
                            y: this.state.deviceEnergyData.map(d => d.energy),
                            type: 'scatter',
                            marker: {
                                color: "#10555e",
                                thickness: 2
                            }
                        }]} layout={{
                            title: "Hourly Individual Energy Consumption",
                            autosize: true,
                            xaxis: {
                                title: {
                                    text: 'Hour',
                                },
                            },
                            yaxis: {
                                title: {
                                    text: 'Energy consumption [kWh]',
                                }
                            }
                        }}/>
                    </div>
                </div>
            </div>
        );
    }
}
