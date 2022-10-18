package all.util
/**
 * Created by @micurtis @vdiachuk
 *
 * Class to hold different addresses used for shipping and billing. Use same format when adding new addresses.
 * Actual Addresses stored in static Map fields
 *
 */

class Addresses {
    String name = ''
    String companyName = ''
    String address = ''
    String address2 = ''
    String zip = ''
    String country = ''
    String countryCode = ''
    String state = ''
    String destinationType = ''
    String city = ''
    String territory = ''
    String phone = ''
    String altPhone = ''

    //Simple Constructor
    Addresses() {}

    /**
     * Constructor Creates Address obj from map of value
     * Missing parameters replaced by empty String
     * ex of use  Addresses addresses = new Addresses(Addresses.usResidentialAddressAK)
     */
    Addresses(Map map) {
        this.name = map['name'] ?: ''
        this.companyName = map['companyName'] ?: ''
        this.address = map['address'] ?: ''
        this.address2 = map['address2'] ?: ''
        this.zip = map['zip'] ?: ''
        this.country = map['country'] ?: ''
        this.countryCode = map['countryCode'] ?: ''
        this.state = map['state'] ?: ''
        this.destinationType = map['destinationType'] ?: ''
        this.city = map['city'] ?: ''
        this.territory = map['territory'] ?: ''
        this.phone = map['phone'] ?: ''
        this.altPhone = map['altPhone'] ?: ''
    }

    /**
     * Return Address fields as a Map of parameters
     * @return
     */
    Map convertToMap() {
        Map map = [:]
        map['name'] = this.name
        map['companyName'] = this.companyName
        map['address'] = this.address
        map['address2'] = this.address2
        map['zip'] = this.zip
        map['country'] = this.country
        map['countryCode'] = this.countryCode
        map['state'] = this.state
        map['destinationType'] = this.destinationType
        map['city'] = this.city
        map['territory'] = this.territory
        map['phone'] = this.phone
        map['altPhone'] = this.altPhone
        return map
    }


    @Override
    String toString() {
        return "Addresses{" +
                "name='" + name + '\'' +
                ", companyName='" + companyName + '\'' +
                ", address='" + address + '\'' +
                ", address2='" + address2 + '\'' +
                ", zip='" + zip + '\'' +
                ", country='" + country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", state='" + state + '\'' +
                ", destinationType='" + destinationType + '\'' +
                ", city='" + city + '\'' +
                ", territory='" + territory + '\'' +
                ", phone='" + phone + '\'' +
                ", altPhone='" + altPhone + '\'' +
                '}';
    }


    //  ==========================================================
    //               US ADDRESSES: RESIDENTIAL
    //  ==========================================================

    def static usResidentialAddressAL = [
            name           : "Alabama Test Address",
            companyName    : "",
            address        : "1193 County Road 241",
            address2       : "",
            city           : "Elba",
            zip            : "36323",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "AL",
            destinationType: "Residential"
    ]

    def static usResidentialAddressAK = [
            name           : "Alaska Test Address",
            companyName    : "",
            address        : "141 Patterson St",
            address2       : "",
            zip            : "99504-1137",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "AK",
            destinationType: "Residential"
    ]

    def static usResidentialAddressAZ = [
            name           : "Arizona Test Address",
            companyName    : "",
            address        : "110 N Forgeus Ave",
            address2       : "",
            zip            : "85716",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "AZ",
            destinationType: "Residential"
    ]

    def static usResidentialAddressAR = [
            name           : "Arkansas Test Address",
            companyName    : "",
            address        : "2121 Lewisburg Rd",
            address2       : "",
            zip            : "72007",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "AR",
            destinationType: "Residential"
    ]

    def static usResidentialAddressCA = [
            name           : "California Test Address",
            companyName    : "",
            address        : "10776 Birch Bluff Ave",
            address2       : "",
            zip            : "92131",
            phone          : "6145555522",
            city           : "San Diego",
            country        : "United States",
            countryCode    : "US",
            state          : "CA",
            destinationType: "Residential"
    ]

    def static usResidentialAddressCO = [
            name           : "Colorado Test Address",
            companyName    : "",
            address        : "15596 Dawson Creek Dr",
            address2       : "",
            zip            : "80132",
            phone          : "6145555522",
            city           : "Monument",
            country        : "United States",
            countryCode    : "US",
            state          : "CO",
            destinationType: "Residential"
    ]

    def static usResidentialAddressCT = [
            name           : "Connecticut Test Address",
            companyName    : "",
            address        : "26 Huntley Ct",
            address2       : "",
            zip            : "06357",
            phone          : "6145555522",
            city           : "Niantic",
            country        : "United States",
            countryCode    : "US",
            state          : "CT",
            destinationType: "Residential"
    ]

    def static usResidentialAddressDE = [
            name           : "Delaware Test Address",
            companyName    : "",
            address        : "225 Saxondale Ln",
            address2       : "",
            zip            : "19904",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "DE",
            destinationType: "Residential"
    ]

    def static usResidentialAddressFL = [
            name           : "Florida Test Address",
            companyName    : "",
            address        : "16334 Burniston Dr",
            address2       : "",
            zip            : "33647",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "FL",
            destinationType: "Residential"
    ]

    def static usResidentialAddressGA = [
            name           : "Georgia Test Address",
            companyName    : "",
            address        : "3238 Landings North Dr",
            address2       : "",
            zip            : "30331-6272",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "GA",
            destinationType: "Residential"
    ]

    def static usResidentialAddressHI = [
            name           : "Hawaii Test Address",
            companyName    : "",
            address        : "441 Walina St",
            address2       : "",
            zip            : "96815",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "HI",
            destinationType: "Residential"
    ]

    def static usResidentialAddressID = [
            name           : "Idaho Test Address",
            companyName    : "",
            address        : "713 S River St,",
            address2       : "",
            zip            : "83333",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "ID",
            destinationType: "Residential"
    ]

    def static usResidentialAddressIL = [
            name           : "Illinois Test Address",
            companyName    : "",
            address        : "1010 Grove ln",
            address2       : "",
            zip            : "60148-2309",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "IL",
            destinationType: "Residential"
    ]

    def static usResidentialAddressIN = [
            name           : "Indiana Test Address",
            companyName    : "",
            address        : "554 Farley Dr",
            address2       : "",
            zip            : "46214",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "IN",
            destinationType: "Residential"
    ]

    def static usResidentialAddressIA = [
            name           : "Iowa Test Address",
            companyName    : "",
            address        : "550 5th St",
            address2       : "",
            zip            : "50263",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "IA",
            destinationType: "Residential"
    ]

    def static usResidentialAddressKS = [
            name           : "Kansas Test Address",
            companyName    : "",
            address        : "3827 W Westport St",
            address2       : "",
            zip            : "67203",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "KS",
            destinationType: "Residential"
    ]

    def static usResidentialAddressKY = [
            name           : "Kentucky Test Address",
            companyName    : "",
            address        : "1869 Farmview Dr",
            address2       : "",
            zip            : "40515",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "KY",
            destinationType: "Residential"
    ]

    def static usResidentialAddressLA = [
            name           : "Louisiana Test Address",
            companyName    : "",
            address        : "1295 Madrid Ave",
            address2       : "",
            zip            : "70776",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "LA",
            destinationType: "Residential"
    ]

    def static usResidentialAddressME = [
            name           : "Maine Test Address",
            companyName    : "",
            address        : "305 Marden Hill Rd",
            address2       : "",
            zip            : "04354",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "ME",
            destinationType: "Residential"
    ]

    def static usResidentialAddressMD = [
            name           : "Maryland Test Address",
            companyName    : "",
            address        : "211 Mckendree Ave",
            address2       : "",
            zip            : "21401-3623",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "MD",
            destinationType: "Residential"
    ]

    def static usResidentialAddressMA = [
            name           : "Massachusetts Test Address",
            companyName    : "",
            address        : "29 Mill St",
            address2       : "",
            zip            : "01524",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "MA",
            destinationType: "Residential"
    ]

    def static usResidentialAddressMI = [
            name           : "Michigan Test Address",
            companyName    : "",
            address        : "2111 Hamilton St",
            address2       : "",
            zip            : "48842",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "MI",
            destinationType: "Residential"
    ]

    def static usResidentialAddressMN = [
            name           : "Minnesota Test Address",
            companyName    : "",
            address        : "118 Villa Dr",
            address2       : "",
            zip            : "55051",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "MN",
            destinationType: "Residential"
    ]

    def static usResidentialAddressMS = [
            name           : "Mississippi Test Address",
            companyName    : "",
            address        : "6003 Woods Rd",
            address2       : "",
            zip            : "39466",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "MS",
            destinationType: "Residential"
    ]

    def static usResidentialAddressMO = [
            name           : "Missouri Test Address",
            companyName    : "",
            address        : "114 S Miramiguoa Dr",
            address2       : "",
            zip            : "63080",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "MO",
            destinationType: "Residential"
    ]

    def static usResidentialAddressMT = [
            name           : "Montana Test Address",
            companyName    : "",
            address        : "14 Doe Ln",
            address2       : "",
            zip            : "59644",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "MT",
            destinationType: "Residential"
    ]

    def static usResidentialAddressNE = [
            name           : "Nebraska Test Address",
            companyName    : "",
            address        : "3421 N 80th St",
            address2       : "",
            zip            : "68134-4911",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "NE",
            destinationType: "Residential"
    ]

    def static usResidentialAddressNV = [
            name           : "Nevada Test Address",
            companyName    : "",
            address        : "5913 Harmony Ave",
            address2       : "",
            zip            : "89107-2632",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "NV",
            destinationType: "Residential"
    ]

    def static usResidentialAddressNH = [
            name           : "New Hampshire Test Address",
            companyName    : "",
            address        : "87 Quimby Rd",
            address2       : "",
            zip            : "03223",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "NH",
            destinationType: "Residential"
    ]

    def static usResidentialAddressNJ = [
            name           : "New Jersey Test Address",
            companyName    : "",
            address        : "165 Taft Dr",
            address2       : "",
            zip            : "08724",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "NJ",
            destinationType: "Residential"
    ]

    def static usResidentialAddressNM = [
            name           : "New Mexico Test Address",
            companyName    : "",
            address        : "508 W Highland Ave",
            address2       : "",
            zip            : "87016",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "NM",
            destinationType: "Residential"
    ]

    def static usResidentialAddressNY = [
            name           : "New York Test Address",
            companyName    : "",
            address        : "204 Riverview Rd",
            address2       : "",
            zip            : "12148",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "NY",
            destinationType: "Residential"
    ]

    def static usResidentialAddressNC = [
            name           : "North Carolina Test Address",
            companyName    : "",
            address        : "78 Sentinel Ct",
            address2       : "",
            zip            : "27577",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "NC",
            destinationType: "Residential"
    ]

    def static usResidentialAddressND = [
            name           : "North Dakota Test Address",
            companyName    : "",
            address        : "301 Mckinley Ave",
            address2       : "",
            zip            : "58444",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "ND",
            destinationType: "Residential"
    ]

    def static usResidentialAddressOH = [
            name           : "Ohio Test Address",
            companyName    : "",
            address        : "208 N Fremont St",
            address2       : "",
            zip            : "43105",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "OH",
            destinationType: "Residential"
    ]

    def static usResidentialAddressOK = [
            name           : "Oklahoma Test Address",
            companyName    : "",
            address        : "6545 Raintree Dr",
            address2       : "",
            zip            : "73170-6111",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "OK",
            destinationType: "Residential"
    ]

    def static usResidentialAddressOR = [
            name           : "Oregon Test Address",
            companyName    : "",
            address        : "23289 SW Pine St",
            address2       : "",
            zip            : "97140",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "OR",
            destinationType: "Residential"
    ]

    def static usResidentialAddressPA = [
            name           : "Pennsylvania Test Address",
            companyName    : "",
            address        : "26 Round House Dr",
            address2       : "",
            zip            : "17543-8316",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "PA",
            destinationType: "Residential"
    ]

    def static usResidentialAddressRI = [
            name           : "Rhode Island Test Address",
            companyName    : "",
            address        : "5 Potter St",
            address2       : "",
            zip            : "02840",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "RI",
            destinationType: "Residential"
    ]

    def static usResidentialAddressSC = [
            name           : "South Carolina Test Address",
            companyName    : "",
            address        : "811 W Church St",
            address2       : "",
            zip            : "29010",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "SC",
            destinationType: "Residential"
    ]

    def static usResidentialAddressSD = [
            name           : "South Dakota Test Address",
            companyName    : "",
            address        : "1615 Hilltop Dr",
            address2       : "",
            zip            : "57501",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "SD",
            destinationType: "Residential"
    ]

    def static usResidentialAddressTN = [
            name           : "Tennessee Test Address",
            companyName    : "",
            address        : "128 Cardinal Loop",
            address2       : "",
            zip            : "38555",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "TN",
            destinationType: "Residential"
    ]

    def static usResidentialAddressTX = [
            name           : "Texas Test Address",
            companyName    : "",
            address        : "1433 Violet Ln",
            address2       : "",
            zip            : "78640",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "TX",
            destinationType: "Residential"
    ]

    def static usResidentialAddressUT = [
            name           : "Utah Test Address",
            companyName    : "",
            address        : "3525 N Ruffed Grouse Rd",
            address2       : "",
            zip            : "84005",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "UT",
            destinationType: "Residential"
    ]

    def static usResidentialAddressVT = [
            name           : "Vermont Test Address",
            companyName    : "",
            address        : "32 Patterson St",
            address2       : "",
            zip            : "05641",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "VT",
            destinationType: "Residential"
    ]

    def static usResidentialAddressVA = [
            name           : "Virginia Test Address",
            companyName    : "",
            address        : "224 Bridgewater Dr",
            address2       : "",
            zip            : "22655",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "VT",
            destinationType: "Residential"
    ]

    def static usResidentialAddressWA = [
            name           : "Washington Test Address",
            companyName    : "",
            address        : "369 Cloquallum Rd",
            address2       : "",
            zip            : "98541",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "WA",
            destinationType: "Residential"
    ]

    def static usResidentialAddressWV = [
            name           : "West Virginia Test Address",
            companyName    : "",
            address        : "1365 Vine Dr",
            address2       : "",
            zip            : "25071",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "WV",
            destinationType: "Residential"
    ]

    def static usResidentialAddressWI = [
            name           : "Wisconsin Test Address",
            companyName    : "",
            address        : "8209 Ridges Rd",
            address2       : "",
            zip            : "54202",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "WI",
            destinationType: "Residential"
    ]

    def static usResidentialAddressWY = [
            name           : "Wyoming Test Address",
            companyName    : "",
            address        : "3337 Garden Creek Rd",
            address2       : "",
            zip            : "82601",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "WY",
            destinationType: "Residential"
    ]

    //  ==========================================================
    //                   US ADDRESSES: COMMERCIAL
    //  ==========================================================


    def static usCommercialAddressAL = [
            name           : "Alabama Test Address",
            companyName    : "Walmart Supercenter",
            address        : "890 Odum Rd",
            city           : "Gardendale",
            zip            : "35071",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "AL",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressAK = [
            name           : "Alaska Test Address",
            companyName    : "Walmart Supercenter",
            address        : "3101 A St",
            zip            : "99503",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "AK",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressAZ = [
            name           : "Arizona Test Address",
            companyName    : "Walmart Supercenter",
            address        : "1175 S Arizona Ave",
            zip            : "85286",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "AZ",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressAR = [
            name           : "Arkansas Test Address",
            companyName    : "Walmart Supercenter",
            address        : "133 Arkansas Blvd",
            zip            : "71854",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "AR",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressCA = [
            name           : "Arkansas Test Address",
            companyName    : "Walmart Supercenter",
            address        : "1301 N Victory Pl",
            zip            : "91502",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "CA",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressCO = [
            name           : "Colorado Test Address",
            companyName    : "Walmart Supercenter",
            address        : "3201 E Platte Ave",
            zip            : "80909",
            phone          : "6145555522",
            city           : "Colorado Springs",
            country        : "United States",
            countryCode    : "US",
            state          : "CO",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressCT = [
            name           : "Connecticut Test Address",
            companyName    : "Walmart Supercenter",
            address        : "680 Connecticut Ave",
            zip            : "80909",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "CT",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressDE = [
            name           : "Delaware Test Address",
            companyName    : "Walmart Supercenter",
            address        : "36 Jerome Dr",
            zip            : "19901",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "DE",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressFL = [
            name           : "Florida Test Address",
            companyName    : "Walmart Supercenter",
            address        : "33501 S Dixie Hwy",
            zip            : "33034",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "FL",
            city           : "Florida City",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressGA = [
            name           : "Georgia Test Address",
            companyName    : "Walmart Supercenter",
            address        : "1911 Epps Bridge Pkwy",
            zip            : "30606",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "GA",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressHI = [
            name           : "Hawaii Test Address",
            companyName    : "Walmart Supercenter",
            address        : "700 Keeaumoku St",
            zip            : "96814",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "HI",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressID = [
            name           : "Idaho Test Address",
            companyName    : "Test Company",
            address        : "8300 W Overland Rd",
            zip            : "83709",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "ID",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressIL = [
            name           : "Illinois Test Address",
            companyName    : "Test Company",
            address        : "13221 Rivercrest Dr",
            zip            : "60418",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "IL",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressIN = [
            name           : "Indiana Test Address",
            companyName    : "Test Company",
            address        : "3100 Oakland Ave",
            zip            : "15701",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "IN",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressIA = [
            name           : "Iowa Test Address",
            companyName    : "Test Company",
            address        : "300 Iowa Speedway Dr",
            zip            : "50208",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "IA",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressKS = [
            name           : "Kansas Test Address",
            companyName    : "Test Company",
            address        : "10824 Parallel Pkwy",
            zip            : "66109",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "KS",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressLA = [
            name           : "Louisiana Test Address",
            companyName    : "Test Company",
            address        : "525 N Cities Service Hwy",
            zip            : "70663",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "LA",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressME = [
            name           : "Maine Test Address",
            companyName    : "Test Company",
            address        : "500 Gallery Blvd",
            zip            : "04074",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "ME",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressMD = [
            name           : "Maryland Test Address",
            companyName    : "Test Company",
            address        : "6210 Annapolis Rd",
            zip            : "20784",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "MD",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressMA = [
            name           : "Massachusetts Test Address",
            companyName    : "Test Company",
            address        : "301 Massachusetts Ave",
            zip            : "01462",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "MD",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressMI = [
            name           : "Michigan Test Address",
            companyName    : "Test Company",
            address        : "45555 Michigan Ave",
            zip            : "48188",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "MI",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressMN = [
            name           : "Minnesota Test Address",
            companyName    : "Test Company",
            address        : "700 American Blvd E",
            zip            : "55420",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "MN",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressMS = [
            name           : "Mississippi Test Address",
            companyName    : "Test Company",
            address        : "6811 Southcrest Pkwy",
            zip            : "38671",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "MS",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressMO = [
            name           : "Missouri Test Address",
            companyName    : "Test Company",
            address        : "1701 N Bluff St",
            zip            : "65251",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "MO",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressMT = [
            name           : "Montana Test Address",
            companyName    : "Test Company",
            address        : "2750 Prospect Ave",
            zip            : "59601",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "MT",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressNE = [
            name           : "Nebraska Test Address",
            companyName    : "Test Company",
            address        : "10504 S 15th St",
            zip            : "68123",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "NE",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressNV = [
            name           : "Nevada Test Address",
            companyName    : "Test Company",
            address        : "4505 W Charleston Blvd",
            zip            : "89102",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "NV",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressNH = [
            name           : "New Hampshire Test Address",
            companyName    : "Test Company",
            address        : "326 N Broadway",
            zip            : "03079",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "NH",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressNJ = [
            name           : "New Jersey Test Address",
            companyName    : "Test Company",
            address        : "400 Park Plaza",
            zip            : "07094",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "NJ",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressNM = [
            name           : "New Mexico Test Address",
            companyName    : "Test Company",
            address        : "233 S New York Ave",
            zip            : "88310",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "NM",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressNY = [
            name           : "New York Test Address",
            companyName    : "Test Company",
            address        : "77 Green Acres Rd S",
            zip            : "11581",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "NY",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressNC = [
            name           : "North Carolina Test Address",
            companyName    : "Test Company",
            address        : "7735 N Tryon St",
            zip            : "28262",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "NC",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressND = [
            name           : "North Dakota Test Address",
            companyName    : "Test Company",
            address        : "1400 Skyline Blvd",
            zip            : "58503",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "ND",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressOH = [
            name           : "Ohio Test Address",
            companyName    : "Test Company",
            address        : "100 Walmart Dr",
            zip            : "45640",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "OH",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressOK = [
            name           : "Oklahoma Test Address",
            companyName    : "Test Company",
            address        : "6100 W Reno Ave",
            zip            : "73127",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "OK",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressOR = [
            name           : "Oregon Test Address",
            companyName    : "Test Company",
            address        : "1360 Center Dr",
            zip            : "97501",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "OR",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressPA = [
            name           : "Pennsylvania Test Address",
            companyName    : "Test Company",
            address        : "12751 Washington Twp Blvd",
            zip            : "17268",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "PA",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressRI = [
            name           : "Rhode Island Test Address",
            companyName    : "Test Company",
            address        : "51 Silver Spring St",
            zip            : "02904",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "RI",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressSC = [
            name           : "South Carolina Test Address",
            companyName    : "Test Company",
            address        : "2401 Augusta Rd",
            zip            : "29169",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "SC",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressSD = [
            name           : "South Dakota Test Address",
            companyName    : "Test Company",
            address        : "1730 N Garfield Ave",
            zip            : "57501",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "SD",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressTN = [
            name           : "Tennessee Test Address",
            companyName    : "Test Company",
            address        : "1414 Parkway",
            zip            : "37862",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "TN",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressTX = [
            name           : "Texas Test Address",
            companyName    : "Test Company",
            address        : "765 Hurst St",
            zip            : "75935",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "TX",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressUT = [
            name           : "Utah Test Address",
            companyName    : "Test Company",
            address        : "350 Hope Ave",
            zip            : "84115",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "UT",
            destinationType: "Commercial"
    ]


    def static usCommercialAddressVT = [
            name           : "Vermont Test Address",
            companyName    : "Test Company",
            address        : "863 Harvest Ln",
            zip            : "05495",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "VT",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressVA = [
            name           : "Virginia Test Address",
            companyName    : "Test Company",
            address        : "24635 Dulles Landing Dr",
            zip            : "20166",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "VA",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressWA = [
            name           : "Washington Test Address",
            companyName    : "Test Company",
            address        : "5900 Littlerock Rd SW",
            zip            : "98512",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "WA",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressWV = [
            name           : "West Virginia Test Address",
            companyName    : "Test Company",
            address        : "96 Patrick Henry Way",
            zip            : "25414",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "WV",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressWI = [
            name           : "Wisconsin Test Address",
            companyName    : "Test Company",
            address        : "4331 8th St S",
            zip            : "54494",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "WI",
            destinationType: "Commercial"
    ]

    def static usCommercialAddressWY = [
            name           : "Wyoming Test Address",
            companyName    : "Test Company",
            address        : "4308 Grand Ave",
            zip            : "82070",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "WY",
            destinationType: "Commercial"
    ]


    //  ==========================================================
    //                   NON US STATE ADDRESSES
    //  ==========================================================

    def static usCommercialAddressVI = [
            name           : "VirginIsland Test Address",
            companyName    : "Walmart Supercenter",
            address        : "8230 Shibui Condo",
            zip            : "00802",
            phone          : "+6145555522",
            country        : "US Virgin Islands",
            countryCode    : "VI",
            city           : "ST THOMAS",
            destinationType: "Commercial"
    ]


    //  ==========================================================
    //                   INTERNATIONAL ADDRESSES
    //  ==========================================================


    def static australiaAddress = [
            name           : "Australia Test Address",
            companyName    : "Test Company Name",
            address        : "22 Bronhill Ave",
            address2       : "",
            zip            : "B1P 7H9",
            phone          : "+61 432511036",
            country        : "Australia",
            countryCode    : "AU",
            city           : "East Ryde",
            territory      : "NSW",
            destinationType: "Commercial"
    ]


    def static austriaAddress = [
            name           : "Austria Test Address",
            companyName    : "Test Company Name",
            address        : "Fasangasse 7",
            address2       : "",
            zip            : "1030 Wien",
            phone          : "+61 432511036",
            country        : "Austria",
            countryCode    : "AT",
            city           : "Vienna",
            destinationType: "Commercial"
    ]


    def static canadaCommercialAddress = [
            name           : "Canada Test Address",
            companyName    : "Walmart Supercenter",
            address        : "165 N Queen St",
            address2       : "",
            zip            : "M9C 1A7",
            phone          : "9093213165",
            country        : "Canada",
            countryCode    : "CA",
            city           : "Etobicoke",
            state          : "ON",
            destinationType: "Commercial"
    ]

    def static mexicoCommercialAddress = [
            name           : "Mexico Test Address",
            companyName    : "Walmart Supercenter",
            address        : "Calz. de Las Armas 61",
            address2       : "",
            zip            : "02710",
            phone          : "+528000000096",
            country        : "Mexico",
            countryCode    : "MX",
            city           : "Mexico City",
            state          : "",
            destinationType: "Commercial"
    ]


    def static spainCommercialAddress = [
            name           : "Spain Test Address",
            companyName    : "Mercadona",
            address        : "129 Carrer de la Indústria",
            address2       : "",
            zip            : "08025",
            phone          : "+34934231709",
            country        : "Spain",
            countryCode    : "ES",
            city           : "Barcelona",
            territory      : "",
            destinationType: "Commercial"
    ]


}
