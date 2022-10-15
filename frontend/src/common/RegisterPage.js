import React, {Component} from 'react';
import {Link} from 'react-router-dom'
import {Button, FormControl, FormGroup, FormLabel, FormSelect} from "react-bootstrap";
import {ERROR, Notification, SUCCESS} from "./Notification";
import {Register} from "../auth/Authentication";

export class RegisterPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: {
                name: "",
                role: "",
                username: "",
                password: "",
            },
            notification: {
                show: false,
                type: ERROR,
                message: "",
                fields: []
            }
        }
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();

        Register(this.state.data)
            .then(() => {
                this.setState({
                    ...this.state,
                    notification: {
                        show: true,
                        type: SUCCESS,
                        message: "Successful registration! Please log in!"
                    }
                })
            })
            .catch(error => {
                this.setState({
                    ...this.state,
                    notification: {
                        show: true,
                        type: ERROR,
                        message: error.message,
                        fields: error.errors
                    }
                })
            });
    }

    handleInputChange(event) {
        this.setState({
            ...this.state,
            data: {
                ...this.state.data,
                [event.target.name]: event.target.value
            },
            notification: {
                show: false
            }
        });
    }

    render() {
        return (
            <div className="background-container bg-image d-flex justify-content-center align-items-center">
                <div className="card col-sm-3 border-dark text-left">
                    <form onSubmit={this.handleSubmit} className="card-body">
                        <h3 className="card-title">Register</h3>
                        {this.state.notification.show ? <Notification notification={this.state.notification}/> : <div/>}
                        <></>
                        <FormGroup className="mb-3" controlId="1">
                            <FormLabel>Name</FormLabel>
                            <FormControl type="text" placeholder="Enter name" name="name" required
                                         onChange={this.handleInputChange}/>
                        </FormGroup>
                        <FormGroup className="mb-3" controlId="2">
                            <FormLabel>Role</FormLabel>
                            <FormSelect name="role" onChange={this.handleInputChange} required>
                                <option value="">-- Choose role --</option>
                                <option value="ADMIN">Admin</option>
                                <option value="CLIENT">Client</option>
                            </FormSelect>
                        </FormGroup>
                        <FormGroup className="mb-3" controlId="3">
                            <FormLabel>Username</FormLabel>
                            <FormControl type="text" placeholder="Enter username" name="username" required
                                         onChange={this.handleInputChange}/>
                        </FormGroup>
                        <FormGroup className="mb-3" controlId="4">
                            <FormLabel>Password</FormLabel>
                            <FormControl type="password" placeholder="Enter Password" name="password" required
                                         onChange={this.handleInputChange}/>
                        </FormGroup>
                        <div className="text-center">
                            <Button variant="secondary" type="submit">
                                Register
                            </Button>
                        </div>
                        <center>
                            Already have an account?
                            <Link to={"/login"}>Log in</Link>
                        </center>
                    </form>
                </div>
            </div>
        );
    }
}
