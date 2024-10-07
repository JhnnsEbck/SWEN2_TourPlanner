import React from "react";
import axios from "axios";

const TourDetails = ({ tour, onDelete, onEdit }) => {
    if (!tour) {
        return <div>Select a tour to see the details</div>;
    }

    const onDeleteTour = async () => {
        try {
            await axios.delete(`/tour/id/${tour.id}`);
            onDelete(tour.id);
        } catch (error) {
            console.error("Error deleting the tour", error);
        }
    };

    const onCreateTourReport = async () => {
        const response = await axios.get(`/report/tour/${tour.id}`, {
            responseType: 'blob',
            params: {
                target: "Tour" + tour.id + "_tourReport.pdf"
            },
        });

        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', 'tourReport.pdf');
        document.body.appendChild(link);
        link.click();
        link.parentNode.removeChild(link);
    };

    const onCreateSummaryReport = async () => {
        const response = await axios.get(`/report/summary`, {
            responseType: 'blob',
            params: {
                target: "Tour" + tour.id + "_summaryReport.pdf"
            },
        });

        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', 'tourReport.pdf');
        document.body.appendChild(link);
        link.click();
        link.parentNode.removeChild(link);
    };

    const getPopularityString = (popularity) => {
        const popularityMap = [
            "unpopular",
            "slightly popular",
            "moderately popular",
            "popular",
            "very popular"
        ];
        return popularityMap[popularity];
    };

    const getChildFriendlinessString = (childFriendliness) => {
        const childFriendlinessMap = [
            "not child-friendly",
            "not very child-friendly",
            "ok",
            "child-friendly",
            "very child-friendly"
        ];
        return childFriendlinessMap[childFriendliness];
    }

    return (
        <div>
            <h3>{tour.name}</h3>
            <p>{tour.description}</p>
            <div>
                <b>From:</b><p>{tour.startLocation}</p>
            </div>
            <div>
                <b>To:</b><p>{tour.endLocation}</p>
            </div>
            <div>
                <b>Transport Type:</b> {tour.transportType}
            </div>
            <div>
                <b>Distance:</b> {tour.tourDistance} m
            </div>
            <div>
                <b>Estimated Time:</b> {tour.estimatedTime} minutes
            </div>
            <div>
                <b>Popularity:</b> {getPopularityString(tour.popularity)}
            </div>
            <div class={"formButtons"}>
                <button onClick={onDeleteTour}>Delete Tour</button>
                <button onClick={onCreateTourReport} style={{marginRight: '10px', marginTop: '10px'}}>Create Tour-Report</button>
            </div>
            <button onClick={onCreateSummaryReport} style={{marginTop: '10px'}}>Create Summarize-Report</button>
        </div>
    );
};

export default TourDetails;