import React, {Component} from 'react'
import {Alert} from 'react-bootstrap'

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
            <Alert dismissible={true} onClose={this.hide} className={this.state.type} show={this.state.show}>
                    <p className="alert-heading">{this.state.message}</p>
                <hr/>
                <>
                {
                    this.state.fields.map((e) =>
                        <p>{e.field} - {e.message}</p>)
                }
                </>
            </Alert>
        );
    }
}