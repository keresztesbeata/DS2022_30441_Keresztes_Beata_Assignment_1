import React, {Component} from 'react';
import {Button, FormControl, FormGroup, FormLabel, Table} from "react-bootstrap";
import {GetAccounts} from "./AdminActions";
import {ERROR, Notification} from "../common/Notification";

export class AccountsTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            accounts: [],
            name: "",
            notification: {
                show: false,
                type: ERROR,
                message: "",
                fields: []
            }
        }
        this.handleInputChange = this.handleInputChange.bind(this);
        this.search = this.search.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange(event) {
        event.preventDefault();

        this.setState({
            ...this.state,
            [event.target.name]: event.target.value
        });
    }

    componentDidMount() {
        this.search();
    }

    handleSubmit(event) {
        event.preventDefault();
        this.search();
    }

    search() {
        GetAccounts(this.state.name)
            .then(accountsData => {
                this.setState({
                    account: accountsData,
                    notification: {
                        show: false
                    }
                })
            })
            .catch(error => {
                this.setState({
                    ...this.state,
                    notification: {
                        show: true,
                        type: ERROR,
                        message: error.message,
                        fields: []
                    }
                })
            });
    }

    render() {
        return (
            <div className="content">
                <form onSubmit={this.handleSubmit}>
                    {this.state.notification.show ? <Notification notification={this.state.notification}/> : <div/>}
                    <FormGroup className="mb-3" controlId="1">
                        <FormLabel>Search by name</FormLabel>
                        <FormControl type="text" placeholder="Name..." name="name"
                                     onChange={this.handleInputChange}/>
                    </FormGroup>
                    <Button variant="primary" type="submit">Search</Button>
                </form>
                <Table>
                    <thead>
                    <tr>
                        <th>UUID</th>
                        <th>Name</th>
                        <th>Username</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.accounts.map(account =>
                            <tr>
                                <td>{account.id}</td>
                                <td>{account.name}</td>
                                <td>{account.username}</td>
                            </tr>
                        )
                    }
                    </tbody>
                </Table>
            </div>
        );
    }
}
