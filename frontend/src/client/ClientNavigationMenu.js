import React from 'react'
import {Nav} from "react-bootstrap";

export class ClientNavigationMenu extends React.Component {
    render() {
        return (
            <div className="side-nav">
                <p>Online Energy Utility Platform</p>
                <Nav defaultActiveKey="/client" className="flex-column">
                    <Nav.Link href="/client/my_account">My account</Nav.Link>
                    <Nav.Link href="/client/devices">My Devices</Nav.Link>
                    <Nav.Link href="/client/energy_consumption">Energy consumption</Nav.Link>
                </Nav>
            </div>
        )
    }
}