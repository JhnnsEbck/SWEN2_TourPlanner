import React, { useEffect, useState } from "react";
import axios from "axios";

const LogList = ({ tourId, onDisplayLogForm, onEditLog}) => {
    const [logs, setLogs] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        const fetchLogs = async () => {
            try {
                const response = await axios.get(`/tour-logs/${tourId}`);
                setLogs(response.data);
                setLoading(false);
            } catch (err) {
                setError(err.message);
                setLoading(false);
            }
        };

        if (tourId) {
            fetchLogs();
        }
    }, [tourId]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;

    const onDeleteLog = async (id) => {
        try {
            await axios.delete(`/tour-logs/${id}`);
        } catch (error) {
            console.error("Error deleting the tour", error);
        }
    };

    const filteredLogs = logs.filter(log =>
        log.comment.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <div>
            <h2>Tour Logs
                <button onClick={onDisplayLogForm} style={{marginLeft: '10px'}}>Create Log</button>
            </h2>
            <input
                type="text"
                placeholder="Search logs... (by comment)"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                style={{marginBottom: '10px', width: '100%'}}
            />
            <ul>
                {filteredLogs.map((log) => (
                    <li key={log.id}>
                        <p>Timestamp: {log.timestamp}</p>
                        <p>Comment: {log.comment}</p>
                        <p>Difficulty: {log.difficulty} / 10</p>
                        <p>Total Distance: {log.totalDistance} m</p>
                        <p>Total Time: {log.totalTime}</p>
                        <p>Rating: {log.rating} / 10</p>
                        <button onClick={onEditLog} style={{marginRight: '10px'}}>Placeholder Update Log</button>
                        <button onClick={() => onDeleteLog(log.id)}>Delete Log</button>
                        <br/>
                        <br/>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default LogList;