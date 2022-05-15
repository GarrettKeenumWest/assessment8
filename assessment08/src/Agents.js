import React, { useEffect, useState } from 'react';

function Agents() {
    const [agents, setAgents] = useState([]);
    const [editIndex, setEditIndex] = useState(null);
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

    const handleEdit = (id) => {
        console.log(`Edits ${id}`);
        setEditIndex(id);
        setFormData();
    }

    const handleDelete = (id) => {
        console.log(`Deletes ${id}`);
        fetch(`http://localhost:8080/api/agent/${id}`, {
            method: "DELETE"
        })
        .then(resp => {
            const newAgents = agents.filter(agent => agent.agentId !== id);
            setAgents(newAgents);
        });
    };

    const handleFormChange = (e) => {
        setFormData(e.target.value);
    }

    const handleSubmit = (e) => {
    //     e.preventDefault();
    //     if(editIndex){
    //         console.log(`currently editing ${editIndex}`)
    //     } else {
    //         console.log("add a new agent");
    //         const agent = {
    //             firstName,
    //             middleName,
    //             lastName
    //         };

    //         const init = {
    //             method: "POST",
    //             headers: {
    //                 "Content-Type": "application/json"
    //             },
    //             body: JSON.stringify(agent)
    //         }
    //         fetch("http://localhost:8080/api/agent", init)
    //         .then(response => {
    //             if (response.status == 201) {
    //                 setAgents("");
    //             } else {
    //                 return Promise.reject("POST agent status was not 201");
    //             }
    //         })
    //         .catch(console.error);
    //     }
    }

    return (<div>
        <h3>Agents</h3>
        <hr/>
        <h2>Display Agent</h2>
        <div>
            {
                agents.map(a => {
                    return(
                        <p>{a.firstName} {a.middleName} {a.lastName} <button onClick={() => (handleEdit(a.agentId))}>Edit</button><button onClick={() => (handleDelete(a.agentId))}>Delete</button></p>
                        )
                })
            }
        </div>
        <hr/>
        <div>
            <h2>{(editIndex)?"Edit":"Create"} Agent</h2>
            <form>
                <div>
                    <label htmlFor='firstName'>First Name</label>
                    <input id='firstName'onChange={handleFormChange} name='firstName' type='text'/>
                </div>
                <div>
                    <label htmlFor='middleName'>Middle Name</label>
                    <input id='middleName'onChange={handleFormChange} name='middleName' type='text'/>
                </div>
                <div>
                    <label htmlFor='lastName'>Last Name</label>
                    <input id='lastName' onChange={handleFormChange} name='lastName' type='text'/>
                </div>
                <button onClick={handleSubmit}>{(editIndex)?"Edit":"Create"} Agent</button>
            </form>
        </div>
    </div>)
}

export default Agents;