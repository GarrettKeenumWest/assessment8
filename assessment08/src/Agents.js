import React, { useEffect, useState } from 'react';

function Agents() {
    const [agents, setAgents] = useState([]);
    const [editIndex, setEditIndex] = useState(null);
    const [formData, setFormData] = useState({
        firstName:"",
        middleName:"",
        lastName:"",
        heightInInches:""
    });

    useEffect(() => {
        fetch("http://localhost:8080/api/agent")
        .then(resp=>resp.json())
        .then(data=> {
            setAgents(data);
        })
    }, []);

    const handleEdit = (id) => {
        setEditIndex(id);  
        console.log(`editing ${id}`);
        const editedAgent = {
            firstName: formData.firstName,
            middleName: formData.middleName,
            lastName: formData.lastName,
            heightInInches: formData.heightInInches
        }
        fetch(`http://localhost:8080/api/agent/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(editedAgent)
        })
        .then(resp => resp.json())
        .then(data => {
            setAgents(data);
        })
    }

    const handleAddAgent = (firstName, middleName, lastName, heightInInches) => {
        const newAgent = {
            firstName: firstName,
            middleName: middleName,
            lastName: lastName,
            heightInInches: heightInInches
        }
        console.log(JSON.stringify(newAgent))
        fetch(`http://localhost:8080/api/agent`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(newAgent)
            }).then(resp => {
                return resp.json();
            }).then(agent => {
                console.log(agent);
                setAgents([...agents, agent]);
            });
        setAgents(agents.concat(newAgent));
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
        console.log(e.target.name);
        console.log(e.target.value);
        const newFormData = {...formData};
        newFormData[e.target.name] = e.target.value;
        setFormData(newFormData);
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        if(editIndex){
            handleEdit(e.target.value)
        } else {
            console.log("add a new agent");
            console.log(formData.firstName);
            handleAddAgent(formData.firstName, formData.middleName, formData.lastName, formData.heightInInches);
        }
        setFormData({
            firstName:"",
            middleName:"",
            lastName:"",
            heightInInches:""
        });
    }

    return (<div>
        <h3>Agents</h3>
        <hr/>
        <h2>Display Agent</h2>
        <div>
            {
                agents.map(a => {
                    return(
                        <p>{a.firstName} {a.middleName} {a.lastName} <button onClick={() => handleEdit(a.agentId)}>Edit</button><button onClick={() => handleDelete(a.agentId)}>Delete</button></p>
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
                    <input id='firstName'onChange={handleFormChange} name='firstName' type='text' value={formData.firstName}/>
                </div>
                <div>
                    <label htmlFor='middleName'>Middle Name</label>
                    <input id='middleName'onChange={handleFormChange} name='middleName' type='text' value={formData.middleName}/>
                </div>
                <div>
                    <label htmlFor='lastName'>Last Name</label>
                    <input id='lastName' onChange={handleFormChange} name='lastName' type='text' value={formData.lastName}/>
                </div>
                <div>
                    <label htmlFor='heightInInches'>Height (Inches)</label>
                    <input id='heightInInches' onChange={handleFormChange} name='heightInInches' type='number' value={formData.heightInInches}/>
                </div>
                <button onClick={handleSubmit}>{(editIndex)?"Edit":"Create"} Agent</button>
            </form>
        </div>
    </div>)
}

export default Agents;