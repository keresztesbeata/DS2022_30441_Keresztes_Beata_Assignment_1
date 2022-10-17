import React, {Component} from 'react';
import {AdminNavigationMenu} from "./AdminNavigationMenu";
import {Col, Row} from "react-bootstrap";

export class AdminHomePage extends Component {
    render() {
        return (
            <Row>
                <Col className="col-md-2">
                    <AdminNavigationMenu/>
                </Col>
                <Col  className="col-md-10">

                </Col>
            </Row>
        );
    }
}
