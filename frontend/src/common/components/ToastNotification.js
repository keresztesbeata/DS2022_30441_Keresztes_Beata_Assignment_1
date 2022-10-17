import React, {Component} from 'react'
import {Modal, Toast} from 'react-bootstrap'

export const INFO = {name: "Information!", style: "info-modal"};
export const SUCCESS = {name: "Success!", style: "success-modal"};
export const WARNING = {name: "Warning!", style: "warning-modal"};
export const ERROR = {name: "Error!", style: "error-modal"};

export class ToastNotification extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: this.props.notification.show,
            type: this.props.notification.type,
            message: this.props.notification.message,
            fields: this.props.notification.fields || [],
        }
        this.hide = this.hide.bind(this);
    }

    hide() {
        this.setState({
            ...this.state,
            show: false
        });
    }

    render() {
        return (
            <Toast show={this.state.show}
                   onClose={this.hide}
                   size="lg"
                   aria-labelledby="contained-modal-title-vcenter"
                   centered="true">
                <Toast.Header closeButton className={this.state.type.style}>
                    <Modal.Title>{this.state.type.name}</Modal.Title>
                </Toast.Header>
                <Toast.Body>
                    <p><strong>{this.state.message}</strong></p>
                    {
                        (this.state.fields.length > 0) ?
                            <>
                                {
                                    this.state.fields.map((e) =>
                                        <p>{e.field} - {e.message}</p>)
                                }
                            </>
                            :
                            <div/>
                    }
                </Toast.Body>
            </Toast>
        );
    }
}