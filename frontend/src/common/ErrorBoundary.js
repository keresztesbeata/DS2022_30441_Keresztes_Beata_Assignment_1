import React from 'react'
import {ERROR, ModalNotification} from "./components/ModalNotification";

export class ErrorBoundary extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            show: false,
            errorNotification: {}
        };
        this.hideNotification = this.hideNotification.bind(this);
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

    componentDidCatch(error, info) {
        this.setState({
            ...this.state,
            show: true,
            errorNotification: {
                type: ERROR,
                message: error.message,
                fields: error.errors || []
            }
        })
    }

    render() {
        if (this.state.show) {
            return <ModalNotification notification={this.state.errorNotification} onHide={this.hideNotification}/>;
        }
        return this.props.children;
    }
}
