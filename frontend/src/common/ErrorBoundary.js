import React from 'react'
import {ERROR_TOAST, ToastNotification} from "./components/ToastNotification";

export class ErrorBoundary extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            show: false,
            errorNotification: {}
        };
    }

    componentDidCatch(error, info) {
        this.setState({
            ...this.state,
            show: true,
            errorNotification: {
                type: ERROR_TOAST,
                message: error.message,
                fields: error.errors || []
            }
        })
    }

    render() {
        if (this.state.show) {
            return <ToastNotification notification={this.state.errorNotification}/>;
        }
        return this.props.children;
    }
}
