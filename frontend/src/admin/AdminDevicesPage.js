import React, {Component} from 'react';
import {AdminNavigationMenu} from "./AdminNavigationMenu";
import {GeneralTable} from "../common/components/GeneralTable";
import {DEVICE_ENTITY} from "./AdminActions";

export class AdminDevicesPage extends Component {
    render() {
        const columns = [{
            Header: 'ID',
            accessor: 'id'
        }, {
            Header: 'Address',
            accessor: 'address'
        }, {
            Header: 'Description',
            accessor: 'description'
        }, {
            Header: 'Account ID',
            accessor: 'accountId'
        }]
        const editableColumns = [{
            Header: 'Address',
            accessor: 'address'
        }, {
            Header: 'Description',
            accessor: 'description'
        }]
        const filters = ['id', 'accountId']
        return (
            <div className="page-container">
                <AdminNavigationMenu/>
                <GeneralTable type={DEVICE_ENTITY} columns={columns} editableColumns={editableColumns} filters={filters}/>
            </div>
        );
    }
}
