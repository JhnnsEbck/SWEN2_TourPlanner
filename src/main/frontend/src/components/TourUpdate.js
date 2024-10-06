import React, { useState } from "react";
import axios from "axios";

const TourUpdate = ({ tour, onUpdateTour, onCancel }) => {
    const [name, setName] = useState(tour.name);
    const [description, setDescription] = useState(tour.description);
    const [startLocation, setStartLocation] = useState(tour.startLocation);
    const [endLocation, setEndLocation] = useState(tour.endLocation);
    const [transportType, setTransportType] = useState(tour.transportType);

    const handleUpdate = async () => {
        const updatedTour = {
            ...tour,
            name,
            description,
            startLocation,
            endLocation,
            transportType
        };

        try {
            await axios.put(`/tour/id/${tour.id}`, updatedTour);
            onUpdateTour(updatedTour);
        } catch (error) {
            console.error("Failed to update tour", error);
        }
    };

    return (
        <div>
            <h2>Update Tour</h2>
            <form onSubmit={(e) => { e.preventDefault(); handleUpdate(); }}>
                <div>
                    <label>Name:</label>
                    <input type="text" value={name} onChange={(e) => setName(e.target.value)} />
                </div>
                <div>
                    <label>Description:</label>
                    <textarea value={description} onChange={(e) => setDescription(e.target.value)} />
                </div>
                <div>
                    <label>From:</label>
                    <input type="text" value={startLocation} onChange={(e) => setStartLocation(e.target.value)} />
                </div>
                <div>
                    <label>To:</label>
                    <input type="text" value={endLocation} onChange={(e) => setEndLocation(e.target.value)} />
                </div>
                <div>
                    <label>Transport Type:</label>
                    <select value={transportType} onChange={(e) => setTransportType(e.target.value)}>
                        <option value="driving-car">Driving Car</option>
                        <option value="cycling-regular">Cycling Regular</option>
                        <option value="foot-walking">Foot Walking</option>
                    </select>
                </div>
                <button type="submit" style={{marginRight: '10px'}}>Update Tour</button>
                <button type="button" onClick={onCancel}>Cancel</button>
            </form>
        </div>
    );
};

export default TourUpdate;
