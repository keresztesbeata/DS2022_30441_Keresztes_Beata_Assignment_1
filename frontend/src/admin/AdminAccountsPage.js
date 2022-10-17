import React, {Component} from 'react';
import {AdminNavigationMenu} from "./AdminNavigationMenu";
import {AccountsTable} from "./AccountsTable";

export class AdminAccountsPage extends Component {
    render() {
        return (
            <div className="page-container">
                <AdminNavigationMenu/>
                <AccountsTable/>
            </div>
        );
    }
}
