import React, {Component} from 'react';
import {Button, FormControl, FormLabel, FormSelect, InputGroup, Modal} from "react-bootstrap";

export class InsertAccountModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: {},
        }
        this.handleInputChange = this.handleInputChange.bind(this);
    }

    handleInputChange(event) {
        this.setState({
            data: {
                ...this.state.data,
                [event.target.name]: event.target.value
            }
        });
    }

    render() {
        return (
            <Modal show={this.props.show} onHide={() => this.props.toggleShow(false)} onShow={() => this.props.toggleShow(true)}>
                <Modal.Header closeButton>
                    <h3>Add a new account</h3>
                </Modal.Header>
                <Modal.Body>
                    <InputGroup className='gap-3 mb-3'>
                        <FormLabel>Name : </FormLabel>
                        <FormControl type='text' placeholder="Enter name" name="name" required
                                     onChange={this.handleInputChange}/>
                    </InputGroup>
                    <InputGroup className='gap-3 mb-3'>
                        <FormLabel>Username : </FormLabel>
                        <FormControl type='text' placeholder="Enter username" name="username" required
                                     onChange={this.handleInputChange}/>
                    </InputGroup>
                    <InputGroup className='gap-3 mb-3'>
                        <FormLabel>Role : </FormLabel>
                        <FormSelect name="role" onChange={this.handleInputChange} required>
                            <option value="">-- Select role --</option>
                            <option value="ADMIN">Admin</option>
                            <option value="CLIENT">Client</option>
                        </FormSelect>
                    </InputGroup>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary"
                            onClick={() => this.props.onSave(this.state.data)}>Save
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }
}