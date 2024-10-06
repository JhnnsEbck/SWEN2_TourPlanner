import React, { useState } from "react";
import axios from "axios";

const TourForm = () => {
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [from, setFrom] = useState({ country: '', city: '', street: '' });
    const [to, setTo] = useState({ country: '', city: '', street: '' });
    const [transportType, setTransportType] = useState('driving-car');

    const handleAddressChange = (e, type, field) => {
        const value = e.target.value;
        if (type === 'from') {
            setFrom({ ...from, [field]: value });
        } else {
            setTo({ ...to, [field]: value });
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const newTour = {
            name,
            description,
            startLocation: `${from.country}, ${from.city}, ${from.street}`,
            endLocation: `${to.country}, ${to.city}, ${to.street}`,
            transportType,
            tourDistance: 0,
            estimatedTime: 0,
            routeImagePath: '/images/default.png',
            popularity: 0,
            childFriendliness: 0
        };

        try {
            const response = await axios.post('/tour', newTour, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (response.status === 200) {
                alert('Tour created successfully');
                // Reset the form fields
                setName('');
                setDescription('');
                setFrom({ country: '', city: '', street: '' });
                setTo({ country: '', city: '', street: '' });
                setTransportType('driving-car');
            } else {
                alert('Failed to create tour');
            }
        } catch (error) {
            alert('An error occurred: ' + error.message);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Name:</label>
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Description:</label>
                <textarea
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    required
                />
            </div>
            <div>
                <h3>From Address</h3>
                <div>
                    <label>Country:</label>
                    <input
                        type="text"
                        value={from.country}
                        onChange={(e) => handleAddressChange(e, 'from', 'country')}
                        required
                    />
                </div>
                <div>
                    <label>City:</label>
                    <input
                        type="text"
                        value={from.city}
                        onChange={(e) => handleAddressChange(e, 'from', 'city')}
                        required
                    />
                </div>
                <div>
                    <label>Street:</label>
                    <input
                        type="text"
                        value={from.street}
                        onChange={(e) => handleAddressChange(e, 'from', 'street')}
                        required
                    />
                </div>
            </div>
            <div>
                <h3>To Address</h3>
                <div>
                    <label>Country:</label>
                    <input
                        type="text"
                        value={to.country}
                        onChange={(e) => handleAddressChange(e, 'to', 'country')}
                        required
                    />
                </div>
                <div>
                    <label>City:</label>
                    <input
                        type="text"
                        value={to.city}
                        onChange={(e) => handleAddressChange(e, 'to', 'city')}
                        required
                    />
                </div>
                <div>
                    <label>Street:</label>
                    <input
                        type="text"
                        value={to.street}
                        onChange={(e) => handleAddressChange(e, 'to', 'street')}
                        required
                    />
                </div>
            </div>
            <div>
                <label>Transport Type:</label>
                <select
                    value={transportType}
                    onChange={(e) => setTransportType(e.target.value)}
                >
                    <option value="driving-car">Driving Car</option>
                    <option value="cycling-regular">Cycling Regular</option>
                    <option value="foot-walking">Foot Walking</option>
                </select>
            </div>
            <button type="submit">Create Tour</button>
        </form>
    );
};

export default TourForm;