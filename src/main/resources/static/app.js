import {html, render} from '/webjars/lit-html/lit-html.js'
import {repeat} from '/webjars/lit-html/directives/repeat.js'

const projectTemplate = (projects) => html`
	${repeat(projects, (project, index) => html`
		<div class="col-xl-6">
			<div class="row">
				<div class="col-lg-6 buttonSquareColumn "><div class="buttonSquare ${project.devColor ? project.devColor : ''} "><i class="fa fa-cog"></i><span>${project.jobName}</span></div></div>
				<div class="col-sm-4 col-lg-2 buttonSquareColumn "><div class="buttonSquare ${project.stableColor ? project.stableColor : ''} "><i class="fa fa-code-branch"></i><span>stable</span></div></div>
				<div class="col-sm-4 col-lg-2 buttonSquareColumn "><div class="buttonSquare ${project.stageColor ? project.stageColor : ''} "><i class="fa fa-code-branch"></i><span>stage</span></div></div>
				<div class="col-sm-4 col-lg-2 buttonSquareColumn "><div class="buttonSquare ${project.prodColor ? project.prodColor : ''} "><i class="fa fa-code-branch"></i><span>prod</span></div></div>
			</div>
		</div>
	`)}
`;



var ws = null;
var url = "ws://localhost:8080/sbm-dashboard-data";
 


$(document).ready(function(){
	
	    ws = new WebSocket(url);
	    
	     
	    ws.onmessage = function(dashboardData) {
	       taflan(dashboardData.data);
	    };
	     
	  
	
});

 
function taflan(dashboardData){
	
	dashboardData = JSON.parse(dashboardData);
	
	dashboardData = mergeSort(dashboardData);
	dashboardData.reverse();
	render(projectTemplate(dashboardData), document.getElementById('projects'));
	
}

function merge(left, right) {
	let arr = [];

	while (left.length && right.length) {

		if (left[0].score < right[0].score) {
			arr.push(left.shift());
		}
		else {
			arr.push(right.shift());
		}
	}
	return arr.concat(left.slice().concat(right.slice()));
}

function mergeSort(arr) {
	if (arr.length < 2) {
		return arr;
	}

	const middle = Math.floor(arr.length / 2);
	const left = arr.slice(0, middle);
	const right = arr.slice(middle);

	return merge(mergeSort(left), mergeSort(right));
}