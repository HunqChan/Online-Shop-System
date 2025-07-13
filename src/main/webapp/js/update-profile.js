window.addEventListener("load", function () {
    const provinceSelect = document.getElementById("province");
    const districtSelect = document.getElementById("district");
    const wardSelect = document.getElementById("ward");

    const provinceHidden = document.getElementById("provinceName");
    const districtHidden = document.getElementById("districtName");
    const wardHidden = document.getElementById("wardName");

    const selectedProvinceId = provinceSelect?.dataset?.selected;
    const selectedDistrictId = districtSelect?.dataset?.selected;
    const selectedWardCode = wardSelect?.dataset?.selected;

    const contextPath = document.body.getAttribute("data-context-path") || "";

    // ===============================
    // Load Provinces
    // ===============================
    fetch(`${contextPath}/api/ghn/provinces`)
        .then(res => {
            if (!res.ok) throw new Error("Failed to fetch provinces");
            return res.json();
        })
        .then(data => {
            provinceSelect.innerHTML = '<option value="">-- Select Province --</option>';
            data.forEach(province => {
                console.log("🔍 province item:", JSON.stringify(province));

                const option = document.createElement("option");
                option.value = province.ProvinceID;
                option.textContent = province.ProvinceName;

                if (selectedProvinceId && String(province.ProvinceID) === selectedProvinceId) {
                    option.selected = true;
                    provinceHidden.value = province.ProvinceName;
                    console.log("✔ Province selected from session:", province.ProvinceName);
                }
                provinceSelect.appendChild(option);
            });

            if (selectedProvinceId) {
                loadDistricts(selectedProvinceId);
            }
        })
        .catch(err => {
            console.error("❌ Error loading provinces:", err);
        });

    // ===============================
    // Load Districts
    // ===============================
    function loadDistricts(provinceId) {
        fetch(`${contextPath}/api/ghn/districts?provinceId=${provinceId}`)
            .then(res => {
                if (!res.ok) throw new Error("Failed to fetch districts");
                return res.json();
            })
            .then(data => {
                districtSelect.innerHTML = '<option value="">-- Select District --</option>';
                wardSelect.innerHTML = '<option value="">-- Select Ward --</option>';

                data.forEach(district => {
                    const option = document.createElement("option");
                    option.value = district.DistrictID;
                    option.textContent = district.DistrictName;

                    if (selectedDistrictId && String(district.DistrictID) === selectedDistrictId) {
                        option.selected = true;
                        districtHidden.value = district.DistrictName;
                        console.log("✔ District selected from session:", district.DistrictName);
                    }

                    districtSelect.appendChild(option);
                });

                if (selectedDistrictId) {
                    loadWards(selectedDistrictId);
                }
            })
            .catch(err => {
                console.error("❌ Error loading districts:", err);
            });
    }

    // ===============================
    // Load Wards
    // ===============================
    function loadWards(districtId) {
        fetch(`${contextPath}/api/ghn/wards?districtId=${districtId}`)
            .then(res => {
                if (!res.ok) throw new Error("Failed to fetch wards");
                return res.json();
            })
            .then(data => {
                wardSelect.innerHTML = '<option value="">-- Select Ward --</option>';

                data.forEach(ward => {
                    const option = document.createElement("option");
                    option.value = ward.WardCode;
                    option.textContent = ward.WardName;

                    if (selectedWardCode && ward.WardCode === selectedWardCode) {
                        option.selected = true;
                        wardHidden.value = ward.WardName;
                        console.log("✔ Ward selected from session:", ward.WardName);
                    }

                    wardSelect.appendChild(option);
                });
            })
            .catch(err => {
                console.error("❌ Error loading wards:", err);
            });
    }

    // ===============================
    // Event listeners
    // ===============================
    provinceSelect.addEventListener("change", function () {
        const selectedOption = provinceSelect.options[provinceSelect.selectedIndex];
        provinceHidden.value = selectedOption.textContent;
        districtHidden.value = "";
        wardHidden.value = "";
        loadDistricts(this.value);
    });

    districtSelect.addEventListener("change", function () {
        const selectedOption = districtSelect.options[districtSelect.selectedIndex];
        districtHidden.value = selectedOption.textContent;
        wardHidden.value = "";
        loadWards(this.value);
    });

    wardSelect.addEventListener("change", function () {
        const selectedOption = wardSelect.options[wardSelect.selectedIndex];
        wardHidden.value = selectedOption.textContent;
    });
});
