/*
Author: David Cameron
Date Created: 12/06/2024
Class: CSC 193A - Web Programming
Project Name: CSC 193A - Assignment 8: Introduction to Node

Description: This is a simple Express.js server that listens on port 8000. It has two routes:
    1. /math/circle/:r - This route takes a radius as a parameter and returns the area and circumference of a circle with that radius.
    2. /hello/:first?/:last? - This route takes two optional parameters, first and last, and returns a greeting message with the first and last names.

*/
'use strict';

const express = require('express');
const app = express();

const PORT = process.env.PORT || 8000;


// Excercise: Splendid Circles

app.get('/math/circle/:r', (req, res) => {
    const radius = parseFloat(req.params.r);
    const area = Math.PI * radius * radius;
    const circumference = 2 * Math.PI * radius;
    res.json({ "area" : area, "circumference" : circumference });
});

// Exercise 2: Hello, you!

app.get("/hello/name", (req, res) => {
    res.type("text");

    const firstName = req.query.first;
    const lastName = req.query.last;

    let missingParams = [];
    if (!firstName) missingParams.push("first");
    if (!lastName) missingParams.push("last");

    if (missingParams.length > 0) {
        res.status(400).send(`Missing Required GET parameters: ${missingParams.join(", ")}`);
    } else {
        res.send(`Hello ${firstName} ${lastName}`);
    }
});

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});