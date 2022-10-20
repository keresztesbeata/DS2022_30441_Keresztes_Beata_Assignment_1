import React, {Component} from 'react';
import {AdminNavigationMenu} from "./AdminNavigationMenu";

export class AdminHomePage extends Component {
    render() {
        return (
            <div className="page-container">
                <AdminNavigationMenu/>
            </div>
        );
    }
}
