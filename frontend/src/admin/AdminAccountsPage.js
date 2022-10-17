import React, {Component} from 'react';
import {AdminNavigationMenu} from "./AdminNavigationMenu";
import {AccountsTable} from "./AccountsTable";
import {Col, Row} from "react-bootstrap";

export class AdminAccountsPage extends Component {
    render() {
        return (
            <Row>
                <Col className="col-md-2">
                    <AdminNavigationMenu/>
                </Col>
                <Col  className="col-md-10">
                    <AccountsTable/>
                </Col>
            </Row>
        );
    }
}
