import React, {Component} from 'react';
import {AdminNavigationMenu} from "./AdminNavigationMenu";
import {GeneralTable} from "../common/components/GeneralTable";
import {ACCOUNT_ENTITY} from "./AdminActions";

export class AdminAccountsPage extends Component {
    render() {
        const columns = [{
            Header: 'ID',
            accessor: 'id'
        }, {
            Header: 'Name',
            accessor: 'name'
        }, {
            Header: 'Username',
            accessor: 'username'
        }, {
            Header: 'Role',
            accessor: 'role',
            isCategorical: true,
            categories: ['ADMIN', 'CLIENT']
        }]
        const editableColumns = [{
            Header: 'Name',
            accessor: 'name'
        }, {
            Header: 'Username',
            accessor: 'username'
        }, {
            Header: 'Role',
            accessor: 'role',
            isCategorical: true,
            categories: ['ADMIN', 'CLIENT']
        }]
        const filters = ['id', 'name', 'username']
        return (
            <div className="page-container">
                <AdminNavigationMenu/>
                <GeneralTable type={ACCOUNT_ENTITY} columns={columns} editableColumns={editableColumns}
                              filters={filters}/>
            </div>
        );
    }
}
