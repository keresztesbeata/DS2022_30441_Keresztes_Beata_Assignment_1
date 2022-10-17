import React, {Component} from 'react';

export class InsertAccountModal extends Component {

    handleSaveBtnClick = () => {
        const {columns, onSave} = this.props;
        const newRow = {};
        columns.forEach((column, i) => {
            if (!column.isKey) {
                newRow[column.field] = this.refs[column.field].value;
            }
        }, this);
        // You should call onSave function and give the new row
        onSave(newRow);
    }

    render() {
        const {
            onModalClose,
            onSave,
            columns,
            validateState,
            ignoreEditable,
        } = this.props;
        return (
            <div className="modal-content bg-light modal-dialog-centered w-50 m-auto mt-5 ">
                <h3>Add a new account</h3>
                <div>
                    {
                        columns.map((column, i) => {
                            const {
                                editable,
                                format,
                                field,
                                name,
                                hiddenOnInsert,
                                customInsertEditor
                            } = column;

                            if (customInsertEditor) {
                                return customInsertEditor.getElement()
                            }

                            if (hiddenOnInsert) {
                                // when you want same auto generate value
                                // and not allow edit, for example ID field
                                return null;
                            }
                            const error = validateState[field] ?
                                (<span className='help-block bg-danger'>{validateState[field]}</span>) :
                                null;
                            return (
                                <div className='input-group gap-3 m-3' key={field}>
                                    <label className='form-label'>{name} : </label>
                                    <input className='form-control' ref={field} type='text' defaultValue={''}/>
                                    {error}
                                </div>
                            );
                        })
                    }
                </div>
                <div>
                    <button className='btn btn-danger' onClick={onModalClose}>Leave</button>
                    <button className='btn btn-success'
                            onClick={() => this.handleSaveBtnClick(columns, onSave)}>Confirm
                    </button>
                </div>
            </div>
        );
    }
}