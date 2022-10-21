import React from 'react';
import {Link} from "react-router-dom";
import {Card, CardImg} from "react-bootstrap";

export default function WelcomePage() {
    return (
        <Card className="mt-lg-5 m-auto w-50 gap-lg-3 border-0">
            <CardImg src={require("./../images/icon.png")} className="logo"/>
            <Link className="custom-link" to="/login">Login</Link>
            <Link className="custom-link" to="/register">Register</Link>
        </Card>
    );
}
