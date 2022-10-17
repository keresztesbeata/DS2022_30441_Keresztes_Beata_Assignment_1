import React, {Component} from 'react'
import {Button, Modal} from 'react-bootstrap'

export const INFO = {name: "Information!", style: "info-modal"};
export const SUCCESS = {name: "Success!", style: "success-modal"};
export const WARNING = {name: "Warning!", style: "warning-modal"};
export const ERROR = {name: "Error!", style: "error-modal"};

export class ModalNotification extends Component {
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
            <Modal show={this.state.show}
                   onHide={this.hide}
                   size="lg"
                   aria-labelledby="contained-modal-title-vcenter"
                   centered>
                <Modal.Header closeButton className={this.state.type.style}>
                    <Modal.Title>{this.state.type.name}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
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
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={this.hide}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }
}