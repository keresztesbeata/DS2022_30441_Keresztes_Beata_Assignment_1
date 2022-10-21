import React, {Component} from 'react';
import {AdminNavigationMenu} from "./components/AdminNavigationMenu";

export class AdminDashboardPage extends Component {
    render() {
        return (
            <div className="page-container">
                <AdminNavigationMenu/>
            </div>
        );
    }
}
