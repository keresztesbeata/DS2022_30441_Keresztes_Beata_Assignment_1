import React, {Component} from 'react'
import {Modal, Toast} from 'react-bootstrap'

export const INFO_TOAST = {name: "Information!", style: "info-modal"};
export const SUCCESS_TOAST = {name: "Success!", style: "success-modal"};
export const WARNING_TOAST = {name: "Warning!", style: "warning-modal"};
export const ERROR_TOAST = {name: "Error!", style: "error-modal"};

export class ToastNotification extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: this.props.notification.show
        }
        this.hide = this.hide.bind(this);
    }

    hide() {
        this.setState({
            ...this.state, show: false
        });
    }

    render() {
        return (<Toast show={this.state.show}
                       onClose={this.hide}
                       size="lg"
                       aria-labelledby="contained-modal-title-vcenter"
                       centered="true">
                <Toast.Header closeButton className={this.props.notification.type.style}>
                    <Modal.Title>{this.props.notification.type.name}</Modal.Title>
                </Toast.Header>
                <Toast.Body>
                    <p><strong>{this.props.notification.message}</strong></p>
                    {this.props.notification.fields ? <>
                        {this.props.notification.fields.map((e) => <p>{e.field} - {e.message}</p>)}
                    </> : <div/>}
                </Toast.Body>
            </Toast>);
    }
}