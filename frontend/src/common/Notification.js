import React, {Component} from 'react'
import {Button, Modal} from 'react-bootstrap'

export const INFO = "alert-info";
export const SUCCESS = "alert-success";
export const WARNING = "alert-warning";
export const ERROR = "alert-danger";

export class Notification extends Component {
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
                <Modal.Header closeButton>
                    <Modal.Title>{this.state.message}</Modal.Title>
                </Modal.Header>
                {
                    (this.state.fields.length > 0) ?
                        <Modal.Body>
                            <>
                                {
                                    this.state.fields.map((e) =>
                                        <p>{e.field} - {e.message}</p>)
                                }
                            </>
                        </Modal.Body>
                        :
                        <div/>
                }
                <Modal.Footer>
                    <Button variant="secondary" onClick={this.hide}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }
}