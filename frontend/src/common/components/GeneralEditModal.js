import React, {Component} from 'react';
import {Button, FormControl, FormLabel, FormSelect, InputGroup, Modal} from "react-bootstrap";

export class GeneralEditModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: this.props.data
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
                    <h3>Edit {this.props.type}</h3>
                </Modal.Header>
                <Modal.Body>
                    {
                        this.props.fields.map(field =>
                            field.isCategorical ?
                                <InputGroup className='gap-3 mb-3'>
                                    <FormLabel>{field.Header} : </FormLabel>
                                    <FormSelect name={field.accessor} required onChange={this.handleInputChange}>
                                        <option value="">-- Select {field.Header} --</option>
                                        {
                                            field.categories.map(category =>
                                                <option value={category} selected={this.state.data[field.accessor] === category}>{category}</option>
                                            )
                                        }
                                    </FormSelect>
                                </InputGroup>
                                :
                                <InputGroup className='gap-3 mb-3'>
                                    <FormLabel>{field.Header} : </FormLabel>
                                    <FormControl type='text' placeholder={`Enter ${field.Header}...`} name={field.accessor}
                                                 value={this.state.data[field.accessor]}
                                                 required onChange={this.handleInputChange}/>
                                </InputGroup>
                        )
                    }
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