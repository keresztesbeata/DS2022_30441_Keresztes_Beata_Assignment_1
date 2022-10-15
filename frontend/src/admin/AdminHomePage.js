import React, {Component} from 'react';
import {Logout} from "../auth/Authentication";

export class AdminHomePage extends Component {
    render() {
        return (
            <div className="background-container bg-image d-flex justify-content-center align-items-center">
                <div className="card col-sm-3 border-dark text-left">
                    <p>Welcome admin!</p>
                    <button onClick={Logout}>Log out</button>
                </div>
            </div>
        );
    }
}
