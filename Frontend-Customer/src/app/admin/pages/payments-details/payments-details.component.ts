import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SidebarComponent } from '../../components/admin-sidebar/admin-sidebar.component';
import { AdminNavbarComponent } from '../admin-navbar/admin-navbar.component';
import { Payment , PaymentService } from '../../services/payment.service';
import { Order } from '../../services/order.service';
import { ShippingDetails } from '../../services/shipping-details.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-payment-details',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './payment-details.component.html',
  styleUrls: ['./payment-details.component.scss']
})
export class PaymentDetailsComponent implements OnInit {


  constructor(private PaymentService: PaymentService, private route: ActivatedRoute) {}

  paymentId = 0;

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.paymentId = parseInt(params.get('paymentId') || '0', 10);
      console.log('Extracted paymentId from route:', this.paymentId); // Debug
      if (this.paymentId) {
        this.fetchPayment(this.paymentId);
      } else {
        console.log('No valid paymentId found in route');
      }
    });
  }

  fetchPayment(id: number): void {
    this.PaymentService.getPayment(id).subscribe({
      next: (data) => {
        this.payment = data;
        console.log('PAyment :', data);
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  // Initialize with proper types
  shippingDetails: ShippingDetails = {
    id: 0,
    address: '',
    city: '',
    country: '',
    delivered_at: '',
    estimated_delivery_date: '',
    postal_code: 0,
    recipient_phone: ''
  };

  order: Order = {
    orderId: 0,
    paymentId: 0,
    userId: 0,
    totalPrice: 0,
    email: '',
    username: '',
    status: 'PENDING',
    orderDate: '',
    orderItems: [],
    shippingDetails: this.shippingDetails,
  };

  payment: Payment = {
    id: 0,
    paymentDate: '',
    paymentMethod: '',
    paymentStatus: '',
    transactionId: 0,
    order: this.order
  }
}