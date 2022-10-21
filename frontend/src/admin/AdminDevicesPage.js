import React, {Component} from 'react';
import {AdminNavigationMenu} from "./AdminNavigationMenu";
import {GeneralTable} from "./components/GeneralTable";
import {DEVICE_ENTITY} from "./api/AdminApi";

export class AdminDevicesPage extends Component {
    render() {
        const columns = [{
            Header: 'ID',
            accessor: 'id',
            editable: false,
            required: false,
        }, {
            Header: 'Address',
            accessor: 'address',
            editable: true,
            required: true,
        }, {
            Header: 'Description',
            accessor: 'description',
            editable: true,
            required: true,
        }, {
            Header: 'Account ID',
            accessor: 'accountId',
            editable: false,
            required: false,
        }]
        const filters = ['id', 'address', 'description', 'accountId'];
        return (
            <div className="page-container">
                <AdminNavigationMenu/>
                <GeneralTable type={DEVICE_ENTITY} columns={columns} filters={filters}/>
            </div>
        );
    }
}
