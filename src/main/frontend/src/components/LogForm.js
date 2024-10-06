import React, { useState } from "react";
import axios from "axios";

const LogForm = ({ tourId }) => {
    const [timestamp, setTimestamp] = useState('');
    const [comment, setComment] = useState('');
    const [difficulty, setDifficulty] = useState(1);
    const [totalDistance, setTotalDistance] = useState('');
    const [totalTime, setTotalTime] = useState('');
    const [rating, setRating] = useState(1);

    const handleSubmit = async (e) => {
        e.preventDefault();

        const newLog = {
            timestamp,
            comment,
            difficulty,
            totalDistance,
            totalTime,
            rating,
            tourId,
        };

        try {
            const response = await axios.post('/tour-logs', newLog, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (response.status === 200) {
                alert('Log created successfully');
                // Reset the form fields
                setTimestamp('');
                setComment('');
                setDifficulty(1);
                setTotalDistance('');
                setTotalTime('');
                setRating(1);
            } else {
                alert('Failed to create log');
            }
        } catch (error) {
            alert('An error occurred: ' + error.message);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Timestamp:</label>
                <input
                    type="datetime-local"
                    value={timestamp}
                    onChange={(e) => setTimestamp(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Comment:</label>
                <textarea
                    value={comment}
                    onChange={(e) => setComment(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Difficulty (1-10):</label>
                <input
                    type="number"
                    min="1"
                    max="10"
                    value={difficulty}
                    onChange={(e) => setDifficulty(parseInt(e.target.value))}
                    required
                />
            </div>
            <div>
                <label>Total Distance (in m):</label>
                <input
                    type="number"
                    step="0.01"
                    value={totalDistance}
                    onChange={(e) => setTotalDistance(parseFloat(e.target.value))}
                    required
                />
            </div>
            <div>
                <label>Total Time (e.g., PT2H30M):</label>
                <input
                    type="text"
                    value={totalTime}
                    onChange={(e) => setTotalTime(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Rating (1-10):</label>
                <input
                    type="number"
                    min="1"
                    max="10"
                    value={rating}
                    onChange={(e) => setRating(parseInt(e.target.value))}
                    required
                />
            </div>
            <button type="submit">Create Log</button>
        </form>
    );
};

export default LogForm;
