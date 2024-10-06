import React, { useEffect, useState } from "react";
import axios from "axios";

const TourList = ({ onSelectTour, onCreateTour }) => {
    const [tours, setTours] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        const fetchTours = async () => {
            try {
                const response = await axios.get('/tour');
                setTours(response.data);
                setLoading(false);
            } catch (err) {
                setError(err.message);
                setLoading(false);
            }
        };

        fetchTours();
    }, []);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;

    const filteredTours = tours.filter(tour =>
        tour.name.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <div>
            <h2>Tour List
                <button onClick={onCreateTour} style={{ marginLeft: '10px' }}>Create Tour</button>
            </h2>
            <input
                type="text"
                placeholder="Search tours..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                style={{ marginBottom: '10px', width: '100%' }}
            />
            <ul>
                {filteredTours.map((tour, index) => (
                    <li key={index} onClick={() => onSelectTour(tour)}>
                        {tour.name}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default TourList;
