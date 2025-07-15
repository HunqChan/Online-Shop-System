document.addEventListener("DOMContentLoaded", function () {
    const provinceSelect = document.getElementById("province");
    const districtSelect = document.getElementById("district");
    const wardSelect = document.getElementById("ward");

    const provinceHidden = document.getElementById("provinceName");
    const districtHidden = document.getElementById("districtName");
    const wardHidden = document.getElementById("wardName");

    const contextPath = document.body.getAttribute("data-context-path") || "";

    const selectedValues = {
        provinceName: provinceHidden?.value || "",
        districtName: districtHidden?.value || "",
        wardName: wardHidden?.value || ""
    };

    // ===== Load Provinces =====
    fetch(`${contextPath}/api/ghn/provinces`)
        .then(res => res.json())
        .then(provinces => {
            provinceSelect.innerHTML = `<option value="">Select province</option>`;
            provinces.forEach(p => {
                const option = new Option(p.ProvinceName, p.ProvinceID);
                if (p.ProvinceName === selectedValues.provinceName) option.selected = true;
                provinceSelect.appendChild(option);
            });
            if (provinceSelect.value) {
                loadDistricts(provinceSelect.value);
            }
        })
        .catch(e => console.error("❌ Error loading provinces:", e));

    // ===== Load Districts =====
    provinceSelect.addEventListener("change", function () {
        const selectedOption = this.options[this.selectedIndex];
        provinceHidden.value = selectedOption.text;
        districtHidden.value = "";
        wardHidden.value = "";

        loadDistricts(this.value);
    });

    function loadDistricts(provinceId) {
        if (!provinceId) {
            districtSelect.innerHTML = `<option value="">Select district</option>`;
            wardSelect.innerHTML = `<option value="">Select ward</option>`;
            return;
        }
        fetch(`${contextPath}/api/ghn/districts?province_id=${provinceId}`)
            .then(res => res.json())
            .then(districts => {
                districtSelect.innerHTML = `<option value="">Select district</option>`;
                districts.forEach(d => {
                    const option = new Option(d.DistrictName, d.DistrictID);
                    if (d.DistrictName === selectedValues.districtName) option.selected = true;
                    districtSelect.appendChild(option);
                });
                if (districtSelect.value) {
                    loadWards(districtSelect.value);
                }
            })
            .catch(e => console.error("❌ Error loading districts:", e));
    }

    // ===== Load Wards =====
    districtSelect.addEventListener("change", function () {
        const selectedOption = this.options[this.selectedIndex];
        districtHidden.value = selectedOption.text;
        wardHidden.value = "";

        loadWards(this.value);
    });

    function loadWards(districtId) {
        if (!districtId) {
            wardSelect.innerHTML = `<option value="">Select ward</option>`;
            return;
        }
        fetch(`${contextPath}/api/ghn/wards?district_id=${districtId}`)
            .then(res => res.json())
            .then(wards => {
                wardSelect.innerHTML = `<option value="">Select ward</option>`;
                wards.forEach(w => {
                    const option = new Option(w.WardName, w.WardCode);
                    if (w.WardName === selectedValues.wardName) option.selected = true;
                    wardSelect.appendChild(option);
                });
            })
            .catch(e => console.error("❌ Error loading wards:", e));
    }

    // ===== Update hidden input when ward changes =====
    wardSelect.addEventListener("change", function () {
        const selectedOption = this.options[this.selectedIndex];
        wardHidden.value = selectedOption.text;
    });
});
