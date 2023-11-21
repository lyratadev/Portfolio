	//EVENT LISTENER FOR QUERY LINE
	//document.querySelector("#input").addEventListener("change", suggestion);
	//LISTENER FOR VALIDATION
	
	document.querySelector("#submitButton").addEventListener("click", submitHit);

	//FETCH FUNCTION
	async function fetchData() 
	{
		console.log("fetch");
		let input = document.querySelector("#input").value;
		input = input.toUpperCase();
		console.log(input.toUpperCase());

		let url = `https://api.polygon.io/v1/open-close/crypto/${input}/USD/2022-07-12?adjusted=true&apiKey=m03_7GPpkz55yC8aqclvb_oiILRufpgU`
		let response = await fetch(url);
		let data = await response.json();

		document.querySelector("#symbol").innerHTML = data.symbol;
		document.querySelector("#open").innerHTML = "$" + data.open;
		document.querySelector("#close").innerHTML = "$" + data.close;
		document.querySelector("#day").innerHTML = data.day;

	}

	async function submitHit()
	{	
		console.log("submit successful");
	}

	//VALIDATE
	function validateForm()
	{
		console.log("validating");
		let isValid = true;
		let query = document.querySelector("#input").value;
		if (query.length == 0) {
		document.querySelector("#inputFeedback").innerHTML = "Invalid not enough characters";
		isValid = false;
		}

		if (!isValid) {
		//event.preventDefault();
		console.log("failed due to validation");
		}

		if (isValid)
		{
		document.querySelector("#inputFeedback").innerHTML = "";

		fetchData();
		}
	}


