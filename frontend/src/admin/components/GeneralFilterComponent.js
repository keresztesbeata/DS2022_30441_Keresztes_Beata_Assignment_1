import React, {Component} from 'react';
import {Filter, GetAll} from "../api/AdminApi";
import {Button, FormControl, FormLabel, FormSelect, InputGroup} from "react-bootstrap";
import {GeneralListComponent} from "./GeneralListComponent";

export class GeneralFilterComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            items: [],
            selectedFilter: this.props.filters !== null ? this.props.filters[0] : "",
            filterValue: ""
        }
        this.handleInputChange = this.handleInputChange.bind(this);
        this.onSearch = this.onSearch.bind(this);
    }

    componentDidMount() {
        // get all entities
        GetAll(this.props.type)
            .then(data => {
                this.setState({
                    ...this.state,
                    items: data,
                    hasError: false
                });
                this.selectFirst(data);
            })
            .catch(error => this.props.errorHandler(error));
    }

    selectFirst(data) {
        if(this.props.showList && data != null) {
            this.props.onSelectItem(data[0])
        }
    }

    handleInputChange(event) {
        this.setState({
            ...this.state,
            [event.target.name]: event.target.value
        });
    }

    onSearch() {
        // check if filter is set
        ((this.state.selectedFilter === null || this.state.filterValue === null || this.state.filterValue.trim() === '') ?
                GetAll(this.props.type)
                :
                Filter(this.props.type, this.state.selectedFilter, this.state.filterValue)
        )
            .then(data => {
                console.log(data)
                this.setState({
                    ...this.state,
                    items: data,
                    hasError: false
                });
                this.selectFirst(data);
            })
            .catch(error =>  this.props.errorHandler(error));
    }

    render() {
        return (
            <div>
                <InputGroup className="gap-3 mb-3">
                    <FormLabel>Search by </FormLabel>
                    <FormSelect name="selectedFilter" onChange={this.handleInputChange}>
                        {
                            this.props.filters.map(filter =>
                                <option value={filter}>{filter}</option>
                            )
                        }
                    </FormSelect>
                    <FormControl type='text' name="filterValue"
                                 placeholder={`Enter ${this.state.selectedFilter}...`}
                                 onChange={this.handleInputChange}/>
                    <Button variant="primary" onClick={this.onSearch}>Search</Button>
                </InputGroup>
                {
                    this.props.showList ?
                        <GeneralListComponent fields={this.props.filters}
                                              items={this.state.items}
                                              selectedItem={this.props.selectedItem}
                                              onSelect={this.props.onSelectItem}/>
                        :
                        <div/>
                }
            </div>
        );
    }
}