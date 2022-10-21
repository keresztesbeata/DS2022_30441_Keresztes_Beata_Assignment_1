import React, {Component} from 'react';
import {ERROR, ModalNotification} from "../common/components/ModalNotification";
import {ClientNavigationMenu} from "./components/ClientNavigationMenu";
import {Button, Form} from "react-bootstrap";
import {GetEnergyConsumptionByDay} from "./api/ClientApi";

export class ClientEnergyConsumptionPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            selectedDate: new Date().toISOString().split('T')[0],
            notification: {
                show: false,
                type: ERROR,
                message: "",
                fields: []
            }
        }
        this.hideNotification = this.hideNotification.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
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

    generateReport() {
        GetEnergyConsumptionByDay(this.state.selectedDate)
            .then(data => {
                // todo
                console.log(data)
            })
            .catch(error => this.onError(error));
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
                <Form className="page-content d-flex flex-column gap-4 p-4 w-50 m-auto">
                    <Form.Group>
                        <Form.Label>Select a day:</Form.Label>
                        <Form.Control type="date" name="selectedDate" defaultValue={this.state.selectedDate}
                                      onChange={this.handleInputChange}/>
                    </Form.Group>
                    <Button onSubmit={this.generateReport} variant="success">Generate report</Button>
                </Form>
            </div>
        );
    }
}
