import React from 'react'
import {Nav} from "react-bootstrap";

export class AdminNavigationMenu extends React.Component {
    render() {
        return (
            <div className="side-nav">
                <p>Online Energy Utility Platform</p>
                <Nav defaultActiveKey="/admin" className="flex-column">
                    <Nav.Link href="/admin/my_account">My account</Nav.Link>
                    <Nav.Link href="/admin/accounts">Manage Users</Nav.Link>
                    <Nav.Link href="/admin/devices">Manage Devices</Nav.Link>
                    <Nav.Link href="/admin/link_device">Link Devices to Users</Nav.Link>
                </Nav>
            </div>
        )
    }
}