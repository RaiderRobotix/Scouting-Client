##FRC Scouting Client [![Build Status](https://travis-ci.org/spencerng/Scouting-Client.svg?branch=master)](https://travis-ci.org/spencerng/Scouting-Client)

A Java-based desktop client that processes scouting data from JSON files and pulls data from The Blue Alliance. To be used with the [Android scouting app](https://github.com/spencerng/Scouting-App)

Features:
 
 * Basic GUI to select input/output folders
 * Merges all scouting JSON files into a master file for export
 * Generates a LaTeX-based report for each team, with graphs
 * Generates a summary report with rankings
 * Generates a `.csv` spreadsheet to easily sort data
 * Downloads a `.csv` list of teams and matches for any FRC event from The Blue Alliance
 * Downloads all files for Team 25's events for the current season
 * Easy customization
 
###To Do

**Before Build Season**
 * Ensure LaTeX generation works with a dummy template
 * Create method to sort event matches by alliance and match number

**During Build Season**
 * Update event models to remain consistent with app
 * Update spreadsheet generation
 * Update summary report generation
 * Update TeX template.
