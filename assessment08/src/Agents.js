import React, { useEffect, useState } from 'react';

function Agents() {
    const [agents, setAgents] = useState([]);
    const [editIndex, setEditIndex] = useState();
    const [formData, setFormData] = useState({
        firstName:"",
        middleName:"",
        lastName:""
    });

    useEffect(() => {
        fetch("http://localhost:8080/api/agent")
        .then(resp=>resp.json())
        .then(data=> {
            setAgents(data);
        })
    }, []);

    const handleDelete = () => {
        console.log(`Deletes ${id}`);
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        if(editIndex){

        }
    }

    return (
    <div>
        <h3>Agents</h3>
        <hr/>
            {
                agents.map(a => {
                    return(
                    <div>
                        <span>{a.firstName} {a.lastName} <button>Edit</button></span>
                    <div/>)
                })
            }
    </div>)
}