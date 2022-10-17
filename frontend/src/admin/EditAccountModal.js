import React, {Component} from 'react';
import {Button, FormControl, FormLabel, FormSelect, FormText, InputGroup, Modal} from "react-bootstrap";

export class EditAccountModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: this.props.data,
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
                    <h3>Edit account</h3>
                </Modal.Header>
                <Modal.Body>
                    <InputGroup className='gap-3 mb-3'>
                        <FormLabel>ID : </FormLabel>
                        <FormText>{this.state.data.id}</FormText>
                    </InputGroup>
                    <InputGroup className='gap-3 mb-3'>
                        <FormLabel>Name : </FormLabel>
                        <FormControl type='text' name="name" value={this.state.data.name}
                                     onChange={this.handleInputChange}/>
                    </InputGroup>
                    <InputGroup className='gap-3 mb-3'>
                        <FormLabel>Username : </FormLabel>
                        <FormControl type='text' name="username" value={this.state.data.username}
                                     onChange={this.handleInputChange}/>
                    </InputGroup>
                    <InputGroup className='gap-3 mb-3'>
                        <FormLabel>Role : </FormLabel>
                        <FormSelect name="role" onChange={this.handleInputChange} value={this.state.data.role}>
                            <option value="">-- Select role --</option>
                            <option value="ADMIN">Admin</option>
                            <option value="CLIENT">Client</option>
                        </FormSelect>
                    </InputGroup>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary"
                            onClick={() => this.props.onUpdate(this.state.data)}>Save
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }
}