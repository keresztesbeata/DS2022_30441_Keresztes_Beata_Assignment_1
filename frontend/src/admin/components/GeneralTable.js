import React, {Component} from 'react';
import {Button, FormControl, FormLabel, FormSelect, InputGroup, Table} from "react-bootstrap";
import {Delete, Filter, GetAll, Insert, Update} from "../api/AdminApi";
import {GeneralInsertModal} from "./GeneralInsertModal";
import {GeneralEditModal} from "./GeneralEditModal";
import {ERROR, ModalNotification, SUCCESS} from "../../common/components/ModalNotification";

export class GeneralTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: [],
            edit: false,
            insert: false,
            selectedFilter: (this.props.filters !== null) ? this.props.filters[0] : "",
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
        this.hideNotification = this.hideNotification.bind(this);
        this.confirmDelete = this.confirmDelete.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.onError = this.onError.bind(this);
    }

    onError(error) {
        this.setState({
            ...this.state,
            notification: {
                show: true,
                type: ERROR,
                message: error.message,
                fields: error.errors
            }
        });
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
            .catch(error => this.onError(error));
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
            .catch(error => this.onError(error));
    }

    onDelete(id) {
        Delete(this.props.type, id)
            .then(id => {
                this.setState({
                    ...this.state,
                    data:
                        this.state.data.filter((d) => d.id !== id)
                    ,
                    notification: {
                        show: true,
                        type: SUCCESS,
                        message: `Successfully deleted ${this.props.type} with id ${id}!`
                    }
                })
            })
            .catch(error => this.onError(error));
    }

    onUpdate(row) {
        Update(this.props.type, row)
            .then(data => {
                this.setState({
                    ...this.state,
                    data:
                        this.state.data.map((d) => d.id === data.id ? data : d)
                    ,
                    edit: false,
                    notification: {
                        show: true,
                        type: SUCCESS,
                        message: `Successfully updated ${this.props.type} with id ${data.id}!`
                    }
                })
            })
            .catch(error => this.onError(error));
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
                    insert: false,
                    notification: {
                        show: true,
                        type: SUCCESS,
                        message: `Successfully saved ${this.props.type} with id ${elem.id}!`
                    }
                }))
            })
            .catch(error => this.onError(error));
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

    hideNotification() {
        this.setState({
            ...this.state,
            notification: {
                ...this.state.notification,
                show: false
            }
        });
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
                {this.state.notification.show ?
                    <ModalNotification notification={this.state.notification} onHide={this.hideNotification}/>
                    :
                    <div/>
                }
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
                <GeneralInsertModal type={this.props.type} fields={this.props.columns.filter(field => field.required)}
                                    show={this.state.insert} toggleShow={this.toggleInsert} onSave={this.onInsert}/>
                {this.state.edit ?
                    <GeneralEditModal type={this.props.type} fields={this.props.columns.filter(field => field.editable)}
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
