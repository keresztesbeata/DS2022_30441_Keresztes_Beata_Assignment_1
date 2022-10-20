import React, {Component} from 'react';
import {AdminNavigationMenu} from "./AdminNavigationMenu";
import {GeneralTable} from "../common/components/GeneralTable";
import {ACCOUNT_ENTITY} from "./AdminActions";

export class AdminAccountsPage extends Component {
    render() {
        const columns = [{
            Header: 'ID',
            accessor: 'id',
            editable: false, // the value of the field can be updated
            required: false, // the value of the field is required when a entity is inserted in the table
        }, {
            Header: 'Name',
            accessor: 'name',
            editable: true,
            required: true,
        }, {
            Header: 'Username',
            accessor: 'username',
            editable: true,
            required: true,
        }, {
            Header: 'Role',
            accessor: 'role',
            isCategorical: true,    // the field can have a discrete set of values
            categories: ['ADMIN', 'CLIENT'], // the set of values reserved for the field
            editable: false,
            required: true,
        }]
        const filters = ['id', 'name', 'username', 'role']
        return (
            <div className="page-container">
                <AdminNavigationMenu/>
                <GeneralTable type={ACCOUNT_ENTITY} columns={columns} filters={filters}/>
            </div>
        );
    }
}
