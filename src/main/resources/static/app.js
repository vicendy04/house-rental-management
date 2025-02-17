function roomManager() {
    return {
        rooms: [],
        totalRooms: 0,
        totalPages: 0,
        currentPage: 0,
        searchTerm: '',
        sortField: 'middleTermPrice',
        sortDirection: '',
        loading: false,
        error: null,
        priceSort: '',
        showEditModal: false,
        editForm: {
            id: null,
            roomNumber: '',
            roomType: '',
            address: '',
            nearestStation: '',
            buildingName: '',
            companyId: '',
            shortTermPrice: '',
            middleTermPrice: '',
            status: '',
            imageUrl: ''
        },
        showCreateModal: false,
        createForm: {
            roomNumber: '',
            roomType: '',
            address: '',
            nearestStation: '',
            buildingName: '',
            companyId: '',
            shortTermPrice: '',
            middleTermPrice: '',
            imageUrl: '/images/apartment.jpg'
        },

        init() {
            console.log('Room manager initialized');
            this.fetchRooms();
        },

        handlePriceSort() {
            if (this.priceSort === '') {
                this.sortField = 'middleTermPrice';
                this.sortDirection = '';
            } else {
                this.sortField = 'middleTermPrice';
                this.sortDirection = this.priceSort.toUpperCase();
            }
            this.currentPage = 0;
            this.fetchRooms();
        },

        async fetchRooms() {
            this.loading = true;
            this.error = null;

            try {
                console.log('Fetching rooms...');
                const params = new URLSearchParams({
                    page: this.currentPage
                });

                if (this.searchTerm) {
                    params.append('buildingName', this.searchTerm);
                }

                if (this.sortDirection) {
                    params.append('sort', `${this.sortField},${this.sortDirection}`);
                }

                const url = `/api/rooms?${params}`;
                console.log('Fetching URL:', url);

                const response = await fetch(url);

                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                const data = await response.json();
                console.log('Received data:', data);

                this.rooms = data.content;
                this.totalRooms = data.totalElements;
                this.totalPages = data.totalPages;
            } catch (error) {
                console.error('Error fetching rooms:', error);
                this.error = error.message;
                this.rooms = [];
                this.totalRooms = 0;
                this.totalPages = 0;
            } finally {
                this.loading = false;
            }
        },

        formatPrice(price) {
            return new Intl.NumberFormat('ja-JP').format(price);
        },

        async search() {
            console.log('Searching with term:', this.searchTerm);
            this.currentPage = 0;
            await this.fetchRooms();
        },

        async previousPage() {
            if (this.currentPage > 0) {
                this.currentPage--;
                await this.fetchRooms();
            }
        },

        async nextPage() {
            if (this.currentPage < this.totalPages - 1) {
                this.currentPage++;
                await this.fetchRooms();
            }
        },

        async sort(field) {
            if (this.sortField === field) {
                this.sortDirection = this.sortDirection === 'ASC' ? 'DESC' : 'ASC';
            } else {
                this.sortField = field;
                this.sortDirection = 'ASC';
            }

            this.currentPage = 0;
            await this.fetchRooms();
        },

        openEditModal(room) {
            this.editForm = {
                id: room.id,
                roomNumber: room.roomNumber,
                roomType: room.roomType,
                address: room.address,
                nearestStation: room.nearestStation,
                buildingName: room.buildingName,
                companyId: room.company.id,
                shortTermPrice: room.shortTermPrice,
                middleTermPrice: room.middleTermPrice,
                status: room.status,
                imageUrl: room.imageUrl
            };
            this.showEditModal = true;
        },

        closeEditModal() {
            this.showEditModal = false;
            this.editForm = {
                id: null,
                roomNumber: '',
                roomType: '',
                address: '',
                nearestStation: '',
                buildingName: '',
                companyId: '',
                shortTermPrice: '',
                middleTermPrice: '',
                status: '',
                imageUrl: ''
            };
        },

        async updateRoom() {
            try {
                const response = await fetch(`/api/rooms/${this.editForm.id}`, {
                    method: 'PUT', headers: {
                        'Content-Type': 'application/json',
                    }, body: JSON.stringify(this.editForm)
                });

                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                await this.fetchRooms();
                this.closeEditModal();
            } catch (error) {
                console.error('Error updating room:', error);
                this.error = error.message;
            }
        },

        async deleteRoom(id) {
            if (!confirm('Are you sure you want to delete this room?')) {
                return;
            }

            try {
                const response = await fetch(`/api/rooms/${id}`, {
                    method: 'DELETE'
                });

                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                await this.fetchRooms();
            } catch (error) {
                console.error('Error deleting room:', error);
                this.error = error.message;
            }
        },
        openCreateModal() {
            this.createForm = {
                roomNumber: '',
                roomType: '',
                address: '',
                nearestStation: '',
                buildingName: '',
                companyId: '',
                shortTermPrice: '',
                middleTermPrice: '',
                imageUrl: '/images/apartment.jpg'
            };
            this.showCreateModal = true;
        },

        closeCreateModal() {
            this.showCreateModal = false;
            this.createForm = {
                roomNumber: '',
                roomType: '',
                address: '',
                nearestStation: '',
                buildingName: '',
                companyId: '',
                shortTermPrice: '',
                middleTermPrice: '',
                imageUrl: '/images/apartment.jpg'
            };
        },

        async createRoom() {
            try {
                const response = await fetch('/api/rooms', {
                    method: 'POST', headers: {
                        'Content-Type': 'application/json',
                    }, body: JSON.stringify(this.createForm)
                });

                if (!response.ok) {
                    const errorData = await response.json();
                    throw new Error(errorData.message || `HTTP error! status: ${response.status}`);
                }

                await this.fetchRooms();
                this.closeCreateModal();
            } catch (error) {
                console.error('Error creating room:', error);
                this.error = error.message;
            }
        },
    };
}
