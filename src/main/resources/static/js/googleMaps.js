const apiKey = "AIzaSyDxntkw27-7sWJpO1fm3kWQnrx_5rCkznc";
const url = `https://www.googleapis.com/geolocation/v1/geolocate?key=${apiKey}`;
const userLocation = navigator.geolocation;
let getLocationButton = document.querySelector("#userLocation");


function initMap() {
    let map = new google.maps.Map(document.querySelector("#map"), {
        center: {lat: 22.99297785113601, lng: 120.18681223016014}, //起始位置
        zoom: 18, //調整地圖大小
        draggable:false,  //關閉手動移動地圖
        disableDefaultUI:true, //關閉所有UI
        clickableIcons:false //大頭針無法點擊
    });

    if (getLocationButton) {
        getLocationButton.addEventListener("click", () => {
            if (userLocation) {
                userLocation.getCurrentPosition(printPosition, getLocationError);
            } else {
                alert("無法用戶獲取位置");
            }
        })
    } else {
        console.log("未找到按鈕")
    }

    function printPosition(position) {
        let newElement = document.createElement("div");
        let userCenter = {
            lat:position.coords.latitude,
            lng:position.coords.longitude
        }
        map.setCenter(userCenter);
        new google.maps.Marker({
            position:userCenter, //顯示員工位置的大頭針
            map:map
        })
        newElement.textContent = position.coords.latitude + "," + position.coords.longitude;
        document.getElementById("latitude").value =userCenter.lat;
        document.getElementById("longitude").value = userCenter.lng;
        // document.body.appendChild(newElement);
    }
}


function getLocationError(error) {
    let newElement = document.createElement("div");
    switch (error.code) {
        case error.PERMISSION_DENIED:
            alert("您拒絕提供位置請重新整理頁面")
            // newElement.textContent = "使用者拒絕提供位置";
            // document.body.appendChild(newElement);
            break;
        case error.POSITION_UNAVAILABLE:
            alert("無法獲得您的位置，請重新整理頁面")
            // newElement.textContent = "沒有位置資訊";
            // document.body.appendChild(newElement);
            break;
        case error.TIMEOUT:
            alert("網頁過久無回應，請重新整理")
            // newElement.textContent = "TimeOut";
            // document.body.appendChild(newElement);
            break;
    }
}
window.addEventListener("load",initMap);