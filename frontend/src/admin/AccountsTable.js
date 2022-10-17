import React, {Component} from 'react';
import {BootstrapTable, TableHeaderColumn} from "react-bootstrap-table";
import {DeleteAccount, GetAccounts, UpdateAccount} from "./AdminActions";
import {ERROR, SUCCESS} from "../common/ModalNotification";
import 'react-bootstrap-table/dist/react-bootstrap-table.min.css';
import {ToastNotification} from "../common/ToastNotification";
import {InsertAccountModal} from "./InsertAccountModal";

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
    }

    search() {
        GetAccounts(this.state.name)
            .then(accountsData => {
                this.setState({
                    ...this.state,
                    accounts: accountsData,
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

    onDelete(key) {
        DeleteAccount(key)
            .then(id => {
                this.setState({
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

    onUpdate(row, cellName, cellValue) {
        UpdateAccount(row)
            .then(data => {
                this.setState(prevState => ({
                    ...this.state,
                    accounts: {
                        ...prevState.accounts.map(el =>
                            el.id === row.id ? data : el
                        )
                    },
                    notification: {
                        show: true,
                        type: SUCCESS,
                        message: "Successful update!"
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

    onSave(row, cellName, cellValue) {
        UpdateAccount(row)
            .then(data => {
                this.setState(prevState => ({
                    ...this.state,
                    accounts: [
                        data,
                        ...prevState.accounts
                    ],
                    notification: {
                        show: true,
                        type: SUCCESS,
                        message: `Successfully saved account with id ${data.id}!`
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

    confirmDelete(next, dropRowKeys) {
        const dropRowKeysStr = dropRowKeys.join(',');
        if (window.confirm(`Are you sure you want to delete account with id ${dropRowKeysStr}?`)) {
            next();
        }
    }

    customRoleField = (column, attr, editorClass, ignoreEditable) => {
        return (
            <select className='form-select' {...attr}>
                <option key="1" value="CLIENT">Client</option>
                <option key="2" value="ADMIN">Admin</option>
            </select>
        );
    }

    createCustomModal = (onModalClose, onSave, columns, validateState, ignoreEditable) => {
        const attr = {
            onModalClose, onSave, columns, validateState, ignoreEditable
        };
        return (
            <InsertAccountModal {...attr} />
        );
    }

    render() {
        const options = {
            handleConfirmDeleteRow: this.confirmDelete,
            afterDeleteRow: this.onDelete,
            afterInsertRow: this.onSave,
            insertModal: this.createCustomModal
        };
        const selectRow = {
            mode: 'radio',
            clickToSelect: true
        };
        return (
            <div className="table-content">
                {this.state.notification.show ? <ToastNotification notification={this.state.notification}/> : <div/>}
                <BootstrapTable data={this.state.accounts} striped hover responsive
                                options={options}
                                search
                                insertRow
                                deleteRow
                                selectRow={selectRow}
                                cellEdit={{mode: 'click', afterSaveCell: this.onUpdate}}>
                    <TableHeaderColumn dataField="id" isKey dataAlign="center"
                                       hiddenOnInsert={true}>UUID</TableHeaderColumn>
                    <TableHeaderColumn dataField="name" dataAlign="center" dataSort>Name</TableHeaderColumn>
                    <TableHeaderColumn dataField="username" dataAlign="center" dataSort>Username</TableHeaderColumn>
                    <TableHeaderColumn dataField="role" editable={false} dataAlign="center"
                                       customInsertEditor={{getElement: this.customRoleField}}>Role</TableHeaderColumn>
                </BootstrapTable>
            </div>
        );
    }
}
