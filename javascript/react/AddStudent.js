import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import { ToastContainer, toast } from 'react-toastify';
import Cookies from 'js-cookie';
import {SERVER_URL} from '../constants.js'
import { Link } from 'react-router-dom';

class AddStudent extends Component {
      constructor(props) {
      super(props);
      this.state = {open: true, student:{ } };
    };
    
    handleClickOpen = () => {
      this.setState( {open:true} );
    };

    handleClose = () => {
      this.setState( {open:false} );
    };

    handleChangeName = (event) => {
      this.setState({student:{
      	...this.state.student,
      	name: event.target.value
      		}
  		});
    }

    handleChangeEmail = (event) => {
      this.setState({student:{
      	...this.state.student,
      	email: event.target.value
      		}
  		});
    }

    handleAdd = () => {
       this.addStudent(this.state.student);
       this.handleClose();
    }

    addStudent(student) {

	  fetch(`${SERVER_URL}/Student/add`, 
	  {
			    method: 'POST',
			    headers: {
			      'Content-Type': 'application/json',
			    },
		body: JSON.stringify({ 
      name: student.name, 
      email: student.email, 
      status_code:0}),
			  })
			  .then((response) => {
			    if (response.ok) {
			      this.handleClose();
			      toast.success('Student Added!');
			    } else {
            throw new Error("Failed, STATUS CODE: " + response.status);
			      //throw new Error('Failed to add student');
			    }
			  })
			  .catch((error) => {
			    toast.error(error.message);
			  });
		}
    

    render()  { 
      return (
        <div>
            <Button variant="outlined" color="primary" style={{margin: 10}} onClick={this.handleClickOpen}>
              Add Student
            </Button>
            <Dialog open={this.state.open} onClose={this.handleClose}>
                <DialogTitle>Add Student</DialogTitle>
                <DialogContent  style={{paddingTop: 20}} >
                  <TextField autoFocus fullWidth label="Name" name="name" onChange={this.handleChangeName}  /> 
                  <TextField autoFocus fullWidth label="Email" name="email" onChange={this.handleChangeEmail}  /> 
                </DialogContent>
                <DialogActions>
                  <Button color="secondary" onClick={this.handleClose}>Cancel</Button>
                  <br/>
                  <Button id="Add" color="primary" onClick={this.handleAdd}>Add</Button>
                </DialogActions>
              </Dialog>   
         <div align="left" >
              <div style={{ height: 400, width: '100%', align:"left"}}>
              </div>  
              <hr></hr>
              
              <Button component={Link} 
                      to={{pathname:'/'}}>
                HOME
              </Button>
              <ToastContainer autoClose={1500} style={{ top: '50%', left: '50%', transform: 'translate(-50%, -50%)' }}/> 
          </div>
        </div>
      ); 
    }
}


export default AddStudent;
