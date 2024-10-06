import React, { useState } from "react";
import axios from "axios";

const LogUpdate = ({ log, onUpdateLog, onCancelLog }) => {
    const [comment, setComment] = useState(log.comment);
    const [difficulty, setDifficulty] = useState(log.difficulty);
    const [totalDistance, setTotalDistance] = useState(log.totalDistance);
    const [totalTime, setTotalTime] = useState(log.totalTime);
    const [rating, setRating] = useState(log.rating);

    const handleUpdate = async () => {
        const updatedLog = {
            ...log,
            comment,
            difficulty,
            totalDistance,
            totalTime,
            rating
        };

        try {
            await axios.put(`/tour-logs/${log.id}`, updatedLog);
            onUpdateLog(updatedLog);
        } catch (error) {
            console.error("Failed to update log", error);
        }
    };

    return (
        <div>
            <h2>Update Log</h2>
            <form onSubmit={(e) => { e.preventDefault(); handleUpdate(); }}>
                <div>
                    <label>Comment:</label>
                    <textarea value={comment} onChange={(e) => setComment(e.target.value)} />
                </div>
                <div>
                    <label>Difficulty:</label>
                    <input type="number" value={difficulty} onChange={(e) => setDifficulty(e.target.value)} />
                </div>
                <div>
                    <label>Total Distance:</label>
                    <input type="number" value={totalDistance} onChange={(e) => setTotalDistance(e.target.value)} />
                </div>
                <div>
                    <label>Total Time:</label>
                    <input type="text" value={totalTime} onChange={(e) => setTotalTime(e.target.value)} />
                </div>
                <div>
                    <label>Rating:</label>
                    <input type="number" value={rating} onChange={(e) => setRating(e.target.value)} />
                </div>
                <button type="submit" style={{marginRight: '10px'}}>Update Log</button>
                <button type="button" onClick={onCancelLog}>Cancel</button>
            </form>
        </div>
    );
};

export default LogUpdate;
