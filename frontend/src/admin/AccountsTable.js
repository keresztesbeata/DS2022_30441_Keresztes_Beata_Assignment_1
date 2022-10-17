import React, {Component} from 'react';
import {DeleteAccount, GetAccounts, InsertAccount, UpdateAccount} from "./AdminActions";
import {ERROR, SUCCESS} from "../common/ModalNotification";
import {ToastNotification} from "../common/ToastNotification";
import {InsertAccountModal} from "./InsertAccountModal";
import {Button, Table} from "react-bootstrap";
import {EditAccountModal} from "./EditAccountModal";

export class AccountsTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: [],
            name: "",
            edit: false,
            insert: false,
            selected: {},
            notification: {
                show: false,
                type: ERROR,
                message: "",
                fields: []
            }
        }
        this.search = this.search.bind(this);
        this.onDelete = this.onDelete.bind(this);
        this.onEdit = this.onEdit.bind(this);
        this.onUpdate = this.onUpdate.bind(this);
        this.onInsert = this.onInsert.bind(this);
        this.toggleEdit = this.toggleEdit.bind(this);
        this.toggleInsert = this.toggleInsert.bind(this);
        this.confirmDelete = this.confirmDelete.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
    }

    search() {
        GetAccounts(this.state.name)
            .then(accountsData => {
                this.setState({
                    ...this.state,
                    data: accountsData,
                    notification: {
                        show: false
                    }
                });
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

    componentDidMount() {
        this.search();
    }

    onDelete(id) {
        DeleteAccount(id)
            .then(id => {
                this.setState({
                    ...this.state,
                    data:
                        this.state.data.filter((d) => d.id !== id)
                    ,
                    notification: {
                        show: true,
                        type: SUCCESS,
                        message: `Successfully deleted account with id ${id}!`
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
                        fields: error.errors
                    }
                })
            });
    }

    onUpdate(row) {
        UpdateAccount(row)
            .then(data => {
                this.setState({
                    ...this.state,
                    data:
                        this.state.data.map((d) => d.id === data.id ? data : d)
                    ,
                    notification: {
                        show: true,
                        type: SUCCESS,
                        message: `Successfully updated account with id ${data.id}!`
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
                        fields: error.errors
                    }
                })
            });
    }

    onInsert(row) {
        InsertAccount(row)
            .then(elem => {
                this.setState(prevState => ({
                    ...this.state,
                    data: [
                        elem,
                        ...prevState.data
                    ],
                    notification: {
                        show: true,
                        type: SUCCESS,
                        message: `Successfully saved account with id ${elem.id}!`
                    }
                }))
            })
            .catch(error => {
                this.setState({
                    ...this.state,
                    notification: {
                        show: true,
                        type: ERROR,
                        message: error.message,
                        fields: error.errors
                    }
                })
            });
    }

    confirmDelete(id) {
        if (window.confirm(`Are you sure you want to delete account with id ${id}?`)) {
            this.onDelete(id);
        }
    }

    toggleInsert(visible) {
        this.setState({
            ...this.state,
            insert: visible
        })
    }

    toggleEdit(visible) {
        this.setState({
            ...this.state,
            edit: visible
        })
    }

    handleInputChange(event) {
        this.setState({
            ...this.state,
            selected: {
                ...this.state.selected,
                [event.target.name]: event.target.value
            }
        });
    }

    onEdit(id) {
        this.setState({
            ...this.state,
            selected: this.state.data.find(elem => elem.id === id),
            edit: true
        });
    }

    render() {
        return (
            <div className="table-container">
                {this.state.notification.show ? <ToastNotification notification={this.state.notification}/> : <div/>}
                <Button variant="success" onClick={this.toggleInsert}>+ New</Button>
                <InsertAccountModal show={this.state.insert} toggleShow={this.toggleInsert} onSave={this.onInsert}/>
                {this.state.edit ?
                    <EditAccountModal data={this.state.selected} show={this.state.edit} toggleShow={this.toggleEdit}
                                      onUpdate={this.onUpdate}/>
                    :
                    <div/>
                }
                <Table data={this.state.data} striped hover responsive>
                    <thead>
                    <tr>
                        <th>UUID</th>
                        <th>Name</th>
                        <th>Username</th>
                        <th>Role</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.data.map(elem =>
                            <tr id={elem.id}>
                                <td>{elem.id}</td>
                                <td>{elem.name}</td>
                                <td>{elem.username}</td>
                                <td>{elem.role}</td>
                                <td>
                                    <Button variant="outline-primary"
                                            onClick={() => this.onEdit(elem.id)}>Update</Button>
                                </td>
                                <td>
                                    <Button variant="outline-danger"
                                            onClick={() => this.confirmDelete(elem.id)}>Delete</Button>
                                </td>
                            </tr>
                        )
                    }
                    </tbody>
                </Table>
            </div>
        );
    }
}
