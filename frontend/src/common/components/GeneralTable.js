import React, {Component} from 'react';
import {Button, FormControl, FormLabel, FormSelect, InputGroup, Table} from "react-bootstrap";
import {ERROR, SUCCESS} from "../Utils";
import {ToastNotification} from "./ToastNotification";
import {Delete, Filter, GetAll, Insert, Update} from "../../admin/AdminActions";
import {GeneralInsertModal} from "./GeneralInsertModal";
import {GeneralEditModal} from "./GeneralEditModal";

export class GeneralTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: [],
            edit: false,
            insert: false,
            selectedFilter: (this.props.filters !== null)? this.props.filters[0] : "",
            filterValue: "",
            selectedData: null,
            notification: {
                show: false,
                type: ERROR,
                message: "",
                fields: []
            }
        }
        this.onSearch = this.onSearch.bind(this);
        this.onLoad = this.onLoad.bind(this);
        this.onDelete = this.onDelete.bind(this);
        this.onEdit = this.onEdit.bind(this);
        this.onUpdate = this.onUpdate.bind(this);
        this.onInsert = this.onInsert.bind(this);
        this.toggleEdit = this.toggleEdit.bind(this);
        this.toggleInsert = this.toggleInsert.bind(this);
        this.confirmDelete = this.confirmDelete.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
    }

    onLoad() {
        GetAll(this.props.type)
            .then(data => {
                this.setState({
                    ...this.state,
                    data: data,
                    notification: {
                        show: false
                    }
                });
            })
            .catch(error => {
                this.setState({
                    ...this.state,
                    notification: {
                        show: true,
                        type: ERROR,
                        message: error.message,
                        fields: []
                    }
                })
            });
    }

    componentDidMount() {
        this.onLoad();
    }


    onSearch() {
        // check if filter is set
        ((this.state.selectedFilter === null || this.state.filterValue === null || this.state.filterValue.trim() === '') ?
            GetAll(this.props.type)
            :
            Filter(this.props.type, this.state.selectedFilter, this.state.filterValue)
        )
            .then(data => {
                this.setState({
                    ...this.state,
                    data: data,
                    notification: {
                        show: false
                    }
                });
            })
            .catch(error => {
                this.setState({
                    ...this.state,
                    notification: {
                        show: true,
                        type: ERROR,
                        message: error.message,
                        fields: []
                    }
                })
            });
    }

    onDelete(id) {
        Delete(this.state.type, id)
            .then(id => {
                this.setState({
                    ...this.state,
                    data:
                        this.state.data.filter((d) => d.id !== id)
                    ,
                    notification: {
                        show: true,
                        type: SUCCESS,
                        message: `Successfully deleted account with id ${id}!`
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

    onUpdate(row) {
        Update(this.props.type, row)
            .then(data => {
                this.setState({
                    ...this.state,
                    data:
                        this.state.data.map((d) => d.id === data.id ? data : d)
                    ,
                    notification: {
                        show: true,
                        type: SUCCESS,
                        message: `Successfully updated account with id ${data.id}!`
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

    onInsert(row) {
        Insert(this.props.type, row)
            .then(elem => {
                this.setState(prevState => ({
                    ...this.state,
                    data: [
                        elem,
                        ...prevState.data
                    ],
                    notification: {
                        show: true,
                        type: SUCCESS,
                        message: `Successfully saved ${this.state.type} with id ${elem.id}!`
                    }
                }))
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

    confirmDelete(id) {
        if (window.confirm(`Are you sure you want to delete the ${this.props.type} with id ${id}?`)) {
            this.onDelete(id);
        }
    }

    toggleInsert(visible) {
        this.setState({
            ...this.state,
            insert: visible
        })
    }

    toggleEdit(visible) {
        this.setState({
            ...this.state,
            edit: visible
        })
    }

    handleInputChange(event) {
        this.setState({
            ...this.state,
            [event.target.name]: event.target.value
        });
    }

    onEdit(id) {
        this.setState({
            ...this.state,
            selected: this.state.data.find(elem => elem.id === id),
            edit: true
        });
    }

    render() {
        return (
            <div className="table-container">
                {this.state.notification.show ? <ToastNotification notification={this.state.notification}/> : <div/>}
                <InputGroup className="gap-3 mb-3">
                    <FormLabel>Search by </FormLabel>
                    <FormSelect name="selectedFilter" onChange={this.handleInputChange}>
                        {
                            this.props.filters.map(filter =>
                                <option value={filter}>{filter}</option>
                            )
                        }
                    </FormSelect>
                    <FormControl type='text' name="filterValue" placeholder={`Enter ${this.state.selectedFilter}...`}
                                 onChange={this.handleInputChange}/>
                    <Button variant="primary" onClick={this.onSearch}>Search</Button>
                </InputGroup>
                <Button variant="success" onClick={this.toggleInsert}>+ New</Button>
                <GeneralInsertModal type={this.props.type} fields={this.props.editableColumns}
                                    show={this.state.insert} toggleShow={this.toggleInsert} onSave={this.onInsert}/>
                {this.state.edit ?
                    <GeneralEditModal type={this.props.type} fields={this.props.editableColumns}
                                      data={this.state.selected}
                                      show={this.state.edit} toggleShow={this.toggleEdit} onUpdate={this.onUpdate}/>
                    :
                    <div/>
                }
                <Table data={this.state.data} striped hover responsive>
                    <thead>
                    <tr>
                        {
                            this.props.columns.map(col =>
                                <th>{col.Header}</th>
                            )
                        }
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.data.map(elem =>
                            <tr id={elem.id}>
                                {
                                    this.props.columns.map(col =>
                                        <td>{elem[col.accessor]}</td>
                                    )
                                }
                                <td>
                                    <Button variant="outline-primary"
                                            onClick={() => this.onEdit(elem.id)}>Update</Button>
                                </td>
                                <td>
                                    <Button variant="outline-danger"
                                            onClick={() => this.confirmDelete(elem.id)}>Delete</Button>
                                </td>
                            </tr>
                        )
                    }
                    </tbody>
                </Table>
            </div>
        );
    }
}
